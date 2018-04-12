package com.craut.project.craut.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto implements Dto{
    long id;
    String text;
    Long idInstruction;
    UserDto user;
}
