package com.craut.project.craut.service.dto;

import com.craut.project.craut.model.UserEntity;
import com.craut.project.craut.service.dto.Dto;
import com.craut.project.craut.service.dto.ProjectRequestDto;
import lombok.*;
import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAndTagsRequestDto implements Dto {
    private ProjectRequestDto projectRequestDto;
    private ArrayList<Object> tags;
    private ArrayList<Object> comment;
    private ArrayList<UserEntity> user;
}
