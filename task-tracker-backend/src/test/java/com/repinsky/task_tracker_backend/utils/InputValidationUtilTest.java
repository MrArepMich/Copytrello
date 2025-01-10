package com.repinsky.task_tracker_backend.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.repinsky.task_tracker_backend.constants.InfoMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InputValidationUtilTest {
    private final InputValidationUtil inputValidationUtil = new InputValidationUtil();

    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 32;

    @Test
    void testAcceptableEmailWithNullOrBlank(){
        assertEquals(EMAIL_CANNOT_BE_EMPTY.getValue(), inputValidationUtil.acceptableEmail(null));

        assertEquals(EMAIL_CANNOT_BE_EMPTY.getValue(), inputValidationUtil.acceptableEmail(""));
    }


    @ParameterizedTest
    @ValueSource(strings = {"abc.def-ghi_jkl@domain.co", "testEmail@gmail.com"})
    void testEmailWithAllowedCharacters(String email){
        assertEquals("", inputValidationUtil.acceptableEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"!%&email@icloud.com", "testEmail@!gmail.com"})
    void testEmailWithDisallowedCharacters(String email){
        assertEquals(INVALID_EMAIL_CHARACTERS.getValue(), inputValidationUtil.acceptableEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"testEmail.gmail.com", "testEmail.com"})
    void testIncorrectEmail(String email){
        assertEquals(INCORRECT_EMAIL.getValue(), inputValidationUtil.acceptableEmail(email));
    }


    @Test
    void testAcceptablePasswordWithNullOrBlank(){
        assertEquals(PASSWORD_CANNOT_BE_EMPTY.getValue(), inputValidationUtil.acceptablePassword(null));

        assertEquals(PASSWORD_CANNOT_BE_EMPTY.getValue(), inputValidationUtil.acceptablePassword(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"FDFDF", "testasswordgaf", "testassword404", "testPasHTHTsword404"})
    void testPasswordWithAllowedCharacters(String password){
        assertEquals("", inputValidationUtil.acceptablePassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc.defghijklin,:;co", "test!?-=%m"})
    void testPasswordWithDisallowedCharacters(String password){
        assertEquals(INVALID_PASSWORD_CHARACTERS.getValue(), inputValidationUtil.acceptablePassword(password));
    }

    @Test
    void testIncorrectPasswordLength(){
        assertEquals("The minimum password length must be " + MIN_PASSWORD_LENGTH + " symbols", inputValidationUtil.acceptablePassword("e65G"));

        assertEquals("The maximum password length must be " + MAX_PASSWORD_LENGTH + " symbols", inputValidationUtil.acceptablePassword("testEmail653632JGkelfgflkia46463lahguewhi34iu3453GFHJDgfjnhxf54"));
    }
}
