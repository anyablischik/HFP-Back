package com.craut.project.craut.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto implements Dto{
    String comment;
    Long idproject;
    Long iduser;
}
