package com.craut.project.craut.service.transformer;

import com.craut.project.craut.model.UserRoleEntity;
import com.craut.project.craut.service.dto.AuthUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserTransformer {

    public AuthUserDto makeDto(final UserRoleEntity userRoleEntity) {
        AuthUserDto authUserDto = new AuthUserDto();
        String blocked = "";

        authUserDto.setId(userRoleEntity.getUser().getIdUser());
        authUserDto.setFirstName(userRoleEntity.getUser().getFirstName());
        authUserDto.setLastName(userRoleEntity.getUser().getLastName());
        authUserDto.setEmail(userRoleEntity.getUser().getEmail());
        authUserDto.setUserName(userRoleEntity.getUser().getUserName());
        authUserDto.setPassword(userRoleEntity.getUser().getPassword());
        authUserDto.setRole(userRoleEntity.getRole().getRoleStatus());
        authUserDto.setImage(userRoleEntity.getUser().getImage());
        if(userRoleEntity.getUser().getBlocked() == 1){
            blocked = "1";
        }
        if(userRoleEntity.getUser().getBlocked() == 3){
            blocked = "0";
        }
        authUserDto.setBlocked(blocked);

        return authUserDto;
    }

}
