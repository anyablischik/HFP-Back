package com.craut.project.craut.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrtionRequestDto implements Dto {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String image;


}
