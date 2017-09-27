package com.craut.project.craut.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListDto implements Dto {

    private long id;
    private String username;
    private String role;

    public UserListDto(long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UserListDto() {
    }
}
