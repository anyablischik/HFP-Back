package com.craut.project.craut.controller;

import com.craut.project.craut.model.InstructionEntity;
import com.craut.project.craut.service.dto.*;
import com.craut.project.craut.service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstructionController {

    private final InstructionService instructionService;

    //instructions

    @PostMapping(value = "/createInstruction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public InstructionAndTagsRequestDto finalAll(@RequestBody InstructionAndTagsRequestDto projectRequestDto) {
        return this.instructionService.save(projectRequestDto, projectRequestDto.getTags(),
                projectRequestDto.getSteps(), projectRequestDto.getSection(),
                projectRequestDto.getUserId(), projectRequestDto.getRating(), projectRequestDto.getTitle());
    }

    @PutMapping(value = "/updateInstruction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public InstructionAndTagsRequestDto updateInstructions(@PathVariable("id") long id,
                                                           @RequestBody final InstructionAndTagsRequestDto data) {
        return instructionService.updateInstruction(id, data);
    }

    @GetMapping(value = "/getInstruction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public InstructionAndTagsRequestDto getIdProject(@PathVariable("id") long id) {
        return  instructionService.getInstruction(id);
    }

    @GetMapping(value = "/userInstructions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionAndTagsRequestDto> getInstructions(@PathVariable("id") long id) {
        return  instructionService.getUserInstructions(id);
    }

    @GetMapping(value = "/userInstructions/{id}/limit={limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionAndTagsRequestDto> getInstructions(@PathVariable("id") long id,
                                                              @PathVariable("limit") Integer limit) {
        if (limit == null){
            return  instructionService.getUserInstructions(id);
        }
        else {
            return instructionService.getUserInstructionsWithLimit(id, limit);
        }
    }

    @GetMapping(value = "/instructionsOfSection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionAndTagsRequestDto> getInstructionsSection(@PathVariable("id") long id) {
        return  instructionService.getInstructionsBySections(id);
    }

    @DeleteMapping(value = "/deleteInstruction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteInstruction(@PathVariable("id") long id) {
        return this.instructionService.deleteInstruction(id);
    }

    //step

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

    @RequestMapping(value = "/getStep/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public StepDto projects(@PathVariable("id") long id) {
        return instructionService.getStep(id);
    }

    @DeleteMapping(value = "/deleteStep/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteStep(@PathVariable("id") long id) {
        return this.instructionService.deleteStep(id);
    }

    //section

    @PostMapping(value = "/createSection", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SectionDto createSection(@RequestBody SectionDto SectionData) {
        return this.instructionService.createSection(SectionData);
    }

    @GetMapping(value = "/getSections", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SectionDto> getSections() {
        return instructionService.getSections();
    }

    @GetMapping(value = "/getSection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SectionDto getSection(@PathVariable("id") long id) {
        return  instructionService.getSection(id);
    }

    @DeleteMapping(value = "/deleteSection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteSection(@PathVariable("id") long id) {
        return this.instructionService.deleteSection(id);
    }

    //comments

    @PostMapping(value = "/updateComment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String updateComment(@PathVariable("id") long id, @RequestBody final CommentRequestDto commentRequestDto) {
        instructionService.updateCommentForInstruction(id, commentRequestDto);
        return "success";
    }

    @PostMapping(value = "/createComment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String addComment(@RequestBody final CommentRequestDto commentRequestDto) {
        instructionService.AddComment(commentRequestDto);
        return "success";
    }

    @GetMapping(value = "/getCommentsByInstructionId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ArrayList<CommentRequestDto> getCommentsByInstructionId(@PathVariable("id") long id) {
        return  instructionService.getCommentByInstruction(id);
    }

    @DeleteMapping(value = "/deleteComment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteComment(@PathVariable("id") long id) {
         this.instructionService.deleteCom(id);
         return "success";
    }

    //rating

    @PostMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String getRating(
            @RequestBody final RatingRequestDto rating) {
        return this.instructionService.setRating(rating);
    }

    @GetMapping(value = "/getRating/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public int getRatingByIdInstruction(@PathVariable("id") long id) {

        return  instructionService.getRating(id);
    }

    //search

    @PostMapping(value = "/searcheTag", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionEntity> finalAll(@RequestBody Object tag) {
        return this.instructionService.searcheByTag(tag);
    }
}
