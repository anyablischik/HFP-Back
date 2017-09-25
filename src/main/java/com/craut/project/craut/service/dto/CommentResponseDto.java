package com.craut.project.craut.service.dto;

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
public class CommentResponseDto implements Dto {
    ArrayList<Object> comment;
    ArrayList<UserEntity> user;
}
