package com.craut.project.craut.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author i.katlinsky
 * @since 22.07.2016
 */
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
}
