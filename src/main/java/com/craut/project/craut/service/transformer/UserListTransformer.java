package com.craut.project.craut.service.transformer;

import com.craut.project.craut.model.UserEntity;
import com.craut.project.craut.service.dto.UserListDto;
import org.springframework.stereotype.Component;

@Component
public class UserListTransformer {

    public UserListDto makeDto(final UserEntity user) {
        UserListDto dto = new UserListDto();
        dto.setId(user.getIdUser());
        dto.setUsername(user.getUserName());
        //dto.setRole(user.getRole().name());

        return dto;
    }
}
