package com.craut.project.craut.service.dto;

import com.craut.project.craut.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto implements Dto{
    private String text;
    private String theme;
    private String image;
    private UserEntity user;
}
