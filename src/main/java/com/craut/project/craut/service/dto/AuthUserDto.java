package com.craut.project.craut.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserDto implements Dto {
    private long id;
    private String firstName;
    private String LastName;
    private String email;
    private String userName;
    private String password;
    private String role;
    private String image;
    private String blocked;
}
