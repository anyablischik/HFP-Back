package com.craut.project.craut.security.service;

import com.craut.project.craut.model.UserEntity;
import com.craut.project.craut.model.UserRoleEntity;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.security.exception.ExpiredTokenAuthenticationException;
import com.craut.project.craut.security.exception.InvalidTokenAuthenticationException;
import com.craut.project.craut.security.model.JwtAuthenticationToken;
import com.craut.project.craut.security.model.JwtUserDetails;
import com.craut.project.craut.security.model.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final long MILLIS_IN_SECOND = 1000L;

    @Autowired
    GenericDaoImpl genericDao;
    private final AuthenticationHelper authenticationHelper;

    @Override
    public Authentication authenticate(final Authentication authRequest) {

        String token = StringUtils.trimToNull((String) authRequest.getCredentials());

        TokenPayload tokenPayload = authenticationHelper.decodeToken(token);

        checkIsExpired(tokenPayload.getExp());

        Long userEntityId = tokenPayload.getUserId();
        if (Objects.isNull(userEntityId)) {
            throw new InvalidTokenAuthenticationException("Token does not contain a user id.");
        }

        UserEntity userEntity = (UserEntity) genericDao.findById(new UserEntity(),userEntityId);
        UserRoleEntity userRoleEntity = (UserRoleEntity)genericDao.findByParametr(userEntity,"UserRoleEntity","user");

        if (Objects.isNull(userEntity)) {
            throw new InvalidTokenAuthenticationException("Token does not contain existed user id.");
        }

        JwtUserDetails userDetails = new JwtUserDetails(userRoleEntity);
        return new JwtAuthenticationToken(userDetails);
    }

    private void checkIsExpired(final Long tokenExpirationTime) {
        if ((System.currentTimeMillis() / MILLIS_IN_SECOND) > tokenExpirationTime) {
            throw new ExpiredTokenAuthenticationException();
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}