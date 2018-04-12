package com.craut.project.craut.service.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Dto{
    private long id;
    private String firstName;
    private String email;
    private String userName;
    private String password;
    private String role;
    private String image;
    private String lastName;
}
