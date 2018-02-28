package com.craut.project.craut.controller;

import com.craut.project.craut.model.InstructionEntity;
import com.craut.project.craut.service.dto.*;
import com.craut.project.craut.service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstructionController {

    private final InstructionService instructionService;

//    @PreAuthorize("hasRole('ROLE_VER')")
    @PostMapping(value = "/createInstruction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String finalAll(@RequestBody InstructionAndTagsRequestDto projectRequestDto) {
        return this.instructionService.save(projectRequestDto.getTags(), projectRequestDto.getSteps(), projectRequestDto.getSection(), projectRequestDto.getUserId(), projectRequestDto.getRating(), projectRequestDto.getTitle());
    }

    @PostMapping(value = "/createStep", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String createStep(@RequestBody StepDto data) {
        return this.instructionService.save(data);
    }

    @PostMapping(value = "/updateStep", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String updateStep(@RequestBody final StepDto data) {
        return instructionService.updateStep(data);
    }

    @PostMapping(value = "/getProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionRequestDto> projects(@RequestBody final Long data) {
        instructionService.checkProject();
        return instructionService.getProjects(data);
    }

    @PostMapping(value = "/getUserProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionRequestDto> projects(@RequestBody final Object data) {
        instructionService.checkProject();
        return instructionService.getUserProjects(data);
    }

    @PostMapping(value = "/searcheTag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionEntity> finalAll(@RequestBody Object tag) {
        return this.instructionService.searcheByTag(tag);
    }

//    @PostMapping(value = "/idProject", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.OK)
//    public InstructionAndTagsRequestDto getIdProject(
//            @RequestBody final Object idproject) {
//        return  instructionService.getProject(idproject);
//    }

    @PreAuthorize("hasAnyRole('ROLE_VER','ROLE_USER')")
    @PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String addComment(@RequestBody final CommentRequestDto commentRequestDto) {
        instructionService.AddComment(commentRequestDto);
        return "success";
    }

    @PreAuthorize("hasAnyRole('ROLE_VER','ROLE_USER')")
    @PostMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String getRating(
            @RequestBody final RatingRequestDto rating) {
        return this.instructionService.setRating(rating);
    }
}
