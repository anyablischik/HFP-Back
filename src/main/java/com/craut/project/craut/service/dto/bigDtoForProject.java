package com.craut.project.craut.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.craut.project.craut.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class bigDtoForProject {
    private String content;
    private String dwy;
    private String image;
    private int money;
    private int rating;
    private String name;
    private String purpose;
    ArrayList<Object> comment;
    ArrayList<UserEntity> user;
    private ArrayList<Object> tags;
}
