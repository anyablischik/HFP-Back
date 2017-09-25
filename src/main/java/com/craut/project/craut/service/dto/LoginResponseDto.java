package com.craut.project.craut.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author i.katlinsky
 * @since 21.07.2016
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto implements Dto {
    private String token;

    public LoginResponseDto(final String token) {
        this.token = token;
    }
}
