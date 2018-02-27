package com.craut.project.craut.service.dto;


import com.craut.project.craut.model.InstructionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {
    private Long id;
    private Integer position;
    private String name;
    private String text;
    private Object image;
    private Long instructionId;
}
