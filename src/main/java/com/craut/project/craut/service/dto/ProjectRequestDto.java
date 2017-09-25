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
public class ProjectRequestDto implements Dto{
    private String content;
    private String dwy;
    private String image;
    private int money;
    private int rating;
    private String name;
    private String purpose;
    private String user;
    private String status;
    private int cash;
}
