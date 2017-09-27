package com.craut.project.craut.service;

import com.craut.project.craut.model.UserEntity;
import com.craut.project.craut.model.UserRoleEntity;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.security.SecurityHelper;
import com.craut.project.craut.security.model.JwtUserDetails;
import com.craut.project.craut.security.service.AuthenticationHelper;
import com.craut.project.craut.service.dto.AuthUserDto;
import com.craut.project.craut.service.dto.JsonException;
import com.craut.project.craut.service.dto.LoginRequestDto;
import com.craut.project.craut.service.dto.LoginResponseDto;
import com.craut.project.craut.service.transformer.AuthUserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    GenericDaoImpl genericDaoImpl;
    private final AuthUserTransformer authUserTransformer;
    private final AuthenticationHelper authenticationHelper;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        try {
            String username = Optional.ofNullable(loginRequestDto.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Username should be passed."));

            String password = Optional.ofNullable(loginRequestDto.getPassword())
                    .orElseThrow(() -> new BadCredentialsException("Password should be passed."));
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                    password);
            final Authentication authResult = this.authenticationManager.authenticate(authRequest);

            if (authResult.isAuthenticated()) {
                JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();

                UserEntity user = (UserEntity)genericDaoImpl.findById(new UserEntity(),userDetails.getId());
                UserRoleEntity userRoleEntity = (UserRoleEntity)genericDaoImpl.findByParametr(user,
                        "UserRoleEntity","user");

                if (Objects.isNull(user)) {
                    throw new JsonException("User not exist in system.");
                }

                if(user.getBlocked() == 3) throw new JsonException("user is blocked");
                String token = this.authenticationHelper.generateToken(userDetails.getId());

                return new LoginResponseDto(token);
            } else {
                throw new JsonException("Authentication failed.");
            }

        } catch (BadCredentialsException exception) {
            throw new JsonException("Username or password was incorrect. Please try again.", exception);
        }
    }

    @Transactional(readOnly = true)
    public AuthUserDto getMe() {
        Authentication authentication = SecurityHelper.getAuthenticationWithCheck();
        JwtUserDetails jwtUserDetails = (JwtUserDetails)authentication.getDetails();
        UserEntity user = (UserEntity)genericDaoImpl.findByTwoParametr(jwtUserDetails.getUsername(),
                "UserEntity","userName","password",jwtUserDetails.getPassword());
        UserRoleEntity userRoleEntity = (UserRoleEntity)genericDaoImpl.findByParametr(user,
                "UserRoleEntity","user");
        return authUserTransformer.makeDto(userRoleEntity);
    }
}