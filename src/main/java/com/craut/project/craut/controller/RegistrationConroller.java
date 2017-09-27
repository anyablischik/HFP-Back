package com.craut.project.craut.controller;

import com.craut.project.craut.service.RegistrationService;
import com.craut.project.craut.service.dto.RegistrtionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RegistrationConroller {

    private final RegistrationService registrationService;

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void registration(
            @RequestBody final RegistrtionRequestDto registrtionRequestDto
    ) {
         registrationService.registration(registrtionRequestDto);
    }

    @GetMapping(value = "/verification")
    @ResponseStatus(value = HttpStatus.OK)
    public void verification( HttpServletResponse http,
                              @RequestParam(name = "token") String token
    ) {
        registrationService.verification(token);
        try {
            http.sendRedirect("http://localhost:4200/succesRegistration");
        } catch (IOException ex) {}
    }

}
