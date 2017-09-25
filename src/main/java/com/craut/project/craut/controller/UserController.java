package com.craut.project.craut.controller;

import com.craut.project.craut.model.MessageEntity;
import com.craut.project.craut.model.ProjectEntity;
import com.craut.project.craut.service.UserService;
import com.craut.project.craut.service.dto.MessageRequestDto;
import com.craut.project.craut.service.dto.ProjectRequestDto;
import com.craut.project.craut.service.dto.RegistrtionRequestDto;
import com.craut.project.craut.service.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/update-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String finalAll( @RequestBody final RegistrtionRequestDto registrtionRequestDto
    , @RequestHeader ("Authorization") String token) {
        System.out.println(token);
        return this.userService.update(registrtionRequestDto,token);
    }
    @PostMapping(value = "/block",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String finalAll( @RequestBody ArrayList<Long> blockRequestDto
            , @RequestHeader ("Authorization") String token) {
        return this.userService.blockUser(blockRequestDto,token);
    }
    @PostMapping(value = "/confirmButton",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String finalAll( @RequestBody ArrayList<Long> blockRequestDto) {
        return this.userService.confirmUser(blockRequestDto);
    }
    @PostMapping(value = "/confirmProfile",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String confirmProfile( @RequestBody MessageRequestDto message
            , @RequestHeader ("Authorization") String token) {
        return this.userService.confirmUser(message,token);
    }
    @GetMapping(value = "/getUsers",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserListDto> finalAll(
    ) {
        return this.userService.findAll();
    }
    @GetMapping(value = "/getMessage",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<MessageRequestDto> finalMessage(
    ) {
        return this.userService.findMessage();
    }
    @PostMapping(value = "/searche",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProjectEntity> search(@RequestBody String search)
    {
        return this.userService.fullTextSearch(search);
    }
}
