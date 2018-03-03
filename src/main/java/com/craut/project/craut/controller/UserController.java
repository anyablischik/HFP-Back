package com.craut.project.craut.controller;

import com.craut.project.craut.model.InstructionEntity;
import com.craut.project.craut.service.UserService;
import com.craut.project.craut.service.dto.AuthUserDto;
import com.craut.project.craut.service.dto.MessageRequestDto;
import com.craut.project.craut.service.dto.RegistrtionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/update-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public AuthUserDto finalAll(@RequestBody final RegistrtionRequestDto registrtionRequestDto,
                                @RequestHeader ("Authorization") String token) {
        return this.userService.update(registrtionRequestDto, token);
    }

    @DeleteMapping(value = "/deleteUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteInstruction(@PathVariable("id") long id) {
        return this.userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/block",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String finalAll( @RequestBody ArrayList<Long> blockRequestDto,
                            @RequestHeader ("Authorization") String token) {
        return this.userService.blockUser(blockRequestDto, token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/confirmButton",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String finalAll( @RequestBody ArrayList<Long> blockRequestDto) {
        return this.userService.confirmUser(blockRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/confirmProfile",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String confirmProfile( @RequestBody MessageRequestDto message,
                                  @RequestHeader ("Authorization") String token) {
        return this.userService.confirmUser(message, token);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuthUserDto> finalAll(
    ) {
        return this.userService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/getMessage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<MessageRequestDto> finalMessage(
    ) {
        return this.userService.findMessage();
    }

    @PostMapping(value = "/searche", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<InstructionEntity> search(@RequestBody String search)
    {
        return this.userService.fullTextSearch(search);
    }
}
