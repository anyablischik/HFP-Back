package com.craut.project.craut.service.dto;

import com.craut.project.craut.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructionRequestDto implements Dto{
    private int rating;
    private String name;
    private String user;
    private String sections;
}
