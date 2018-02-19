package com.craut.project.craut.service.dto;

import com.craut.project.craut.model.UserEntity;
import lombok.*;
import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAndTagsRequestDto implements Dto {
    private InstructionRequestDto instructionRequestDto;
    private ArrayList<Object> tags;
    private ArrayList<Object> comment;
    private ArrayList<UserEntity> user;
}
