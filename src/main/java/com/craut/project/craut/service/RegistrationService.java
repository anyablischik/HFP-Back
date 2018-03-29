package com.craut.project.craut.service;

import com.craut.project.craut.model.Generic;
import com.craut.project.craut.model.RolesEntity;
import com.craut.project.craut.model.UserEntity;
import com.craut.project.craut.model.UserRoleEntity;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.security.model.TokenPayload;
import com.craut.project.craut.security.service.AuthenticationHelper;
import com.craut.project.craut.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    @Autowired
    GenericDaoImpl genericDaoImpl;

    private final AuthenticationHelper authenticationHelper;
    private  final PasswordEncoder passwordEncoder;

    public static boolean checkPasswordWithRegExp(String password) {
        Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{8,12}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public void registration(final RegistrtionRequestDto registrtionRequestDto) {

        String username = Optional.ofNullable(registrtionRequestDto.getUserName())
                .orElseThrow(() -> new BadCredentialsException("Username should be passed."));
        UserEntity userEntity = (UserEntity) genericDaoImpl.findByParametr(username,"UserEntity",
                "userName");
        if(userEntity!=null) {
            throw new BadCredentialsException("Username not Unique.");
        }

        if(!checkPasswordWithRegExp(registrtionRequestDto.getPassword())){
            throw new BadCredentialsException("Password incorrect");
        }

        String password = passwordEncoder.encode(Optional.ofNullable(registrtionRequestDto.getPassword())
                .orElseThrow(() -> new BadCredentialsException("Password should be passed.")));

        String firstname = Optional.ofNullable(registrtionRequestDto.getFirstName())
                .orElseThrow(() -> new BadCredentialsException("FirstName should be passed."));

        String secondname = Optional.ofNullable(registrtionRequestDto.getLastName())
                .orElseThrow(() -> new BadCredentialsException("SecondMane should be passed."));

        String email = Optional.ofNullable(registrtionRequestDto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("FirstName should be passed."));

        int blocked = 3;
        UserRoleEntity userRoleEntity = new UserRoleEntity(
                new UserEntity(firstname,secondname,
                        email,username,password,blocked,""),
                (RolesEntity)genericDaoImpl.findById(new RolesEntity(), 1L));
        genericDaoImpl.save(userRoleEntity);
        Verification.Verification(userRoleEntity.getUser().getEmail()
                ,this.authenticationHelper.generateToken(userRoleEntity.getIdusers_roles()));

    }


    public void verification(String token) {
        TokenPayload tokenPayload = authenticationHelper.decodeToken(token);
        Long userRoleEntityId = tokenPayload.getUserId();
        UserRoleEntity userRoleEntity = (UserRoleEntity)genericDaoImpl.findById(new UserRoleEntity(),
                userRoleEntityId);
        userRoleEntity.getUser().setBlocked(1);
        genericDaoImpl.save(userRoleEntity);
    }

}
