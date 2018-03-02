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
    public InstructionAndTagsRequestDto finalAll(@RequestBody InstructionAndTagsRequestDto projectRequestDto) {
        return this.instructionService.save(projectRequestDto, projectRequestDto.getTags(), projectRequestDto.getSteps(), projectRequestDto.getSection(), projectRequestDto.getUserId(), projectRequestDto.getRating(), projectRequestDto.getTitle());
    }

    @PostMapping(value = "/createStep", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public StepDto createStep(@RequestBody StepDto data) {
        return this.instructionService.save(data);
    }

    @PutMapping(value = "/updateStep/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public StepDto updateStep(@PathVariable("id") long id, @RequestBody final StepDto data) {
        return instructionService.updateStep(id, data);
    }

    @PutMapping(value = "/updateInstruction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public InstructionAndTagsRequestDto updateStep(@PathVariable("id") long id, @RequestBody final InstructionAndTagsRequestDto data) {
        return instructionService.updateInstruction(id, data);
    }

    @RequestMapping(value = "/getStep/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StepDto projects(@PathVariable("id") long id) {
        return instructionService.getStep(id);
    }

    @GetMapping(value = "/getSections", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SectionDto> getSections() {
        return instructionService.getSections();
    }

    @GetMapping(value = "/getInstruction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public InstructionAndTagsRequestDto getIdProject(@PathVariable("id") long id) {
        return  instructionService.getInstruction(id);
    }

    @PostMapping(value = "/getUserProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionRequestDto> projects(@RequestBody final Object data) {
        instructionService.checkProject();
        return instructionService.getUserProjects(data);
    }

    @DeleteMapping(value = "/deleteStep/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteStep(@PathVariable("id") long id) {
        return this.instructionService.deleteStep(id);
    }

    @DeleteMapping(value = "/deleteInstruction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteInstruction(@PathVariable("id") long id) {
        return this.instructionService.deleteInstruction(id);
    }


    @PostMapping(value = "/searcheTag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionEntity> finalAll(@RequestBody Object tag) {
        return this.instructionService.searcheByTag(tag);
    }

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
