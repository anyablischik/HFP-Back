package com.craut.project.craut.security.service;

import com.craut.project.craut.model.UserEntity;
import com.craut.project.craut.model.UserRoleEntity;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.security.model.JwtUserDetails;
import com.craut.project.craut.service.dto.JsonException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   GenericDaoImpl genericDaoImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = (UserEntity)genericDaoImpl.findByParametr(username,"UserEntity","userName");
        UserRoleEntity userRoleEntity = (UserRoleEntity)genericDaoImpl.findByParametr(user,"UserRoleEntity","user");

        return Optional.ofNullable(userRoleEntity)
                .map(JwtUserDetails::new)
                .orElseThrow(() -> new JsonException("User nor found."));
    }
}
