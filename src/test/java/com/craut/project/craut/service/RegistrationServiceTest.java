package com.craut.project.craut.service;

import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.*;

public class RegistrationServiceTest {

    @Test
    void checkPasswordMinLength() {
        assertTrue("Password is too short", RegistrationService.checkPasswordWithRegExp("Dima12345"));
    }

    @Test
    void checkPasswordUppercase() {
        assertTrue("Password doesn't contain capital letter", RegistrationService.checkPasswordWithRegExp("Dima12345"));
    }

    @Test
    void checkPasswordRussianLetter() {
        assertTrue("Password contains russian letter", RegistrationService.checkPasswordWithRegExp("Dima12345"));
    }

    @Test
    void checkPasswordMaxLength() {
        assertTrue("Password is too long", RegistrationService.checkPasswordWithRegExp("Dima12785"));
    }

    @Test
    void checkPasswordNumber() {
        assertTrue("Password doesn't contain number", RegistrationService.checkPasswordWithRegExp("Dima12345"));
    }
}