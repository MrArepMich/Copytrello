package com.repinsky.task_tracker_backend.utils;

import java.util.ArrayList;

import org.apache.commons.validator.routines.EmailValidator;

import static com.repinsky.task_tracker_backend.constants.InfoMessage.*;

public class InputValidationUtil {
    private static ArrayList<Character> digits;
    private static ArrayList<Character> latin;
    private static ArrayList<Character> passwordCharacters;
    private static ArrayList<Character> emailCharacters;

    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 32;


    public InputValidationUtil() {
        digits = new ArrayList<>(10);
        digits.add('0'); digits.add('1'); digits.add('2'); digits.add('3'); digits.add('4');
        digits.add('5'); digits.add('6'); digits.add('7'); digits.add('8'); digits.add('9');

        latin = new ArrayList<>(52);
        latin.add('A'); latin.add('B'); latin.add('C'); latin.add('D'); latin.add('E'); latin.add('F'); latin.add('G');
        latin.add('H'); latin.add('I'); latin.add('J'); latin.add('K'); latin.add('L'); latin.add('M'); latin.add('N');
        latin.add('O'); latin.add('P'); latin.add('Q'); latin.add('R'); latin.add('S'); latin.add('T'); latin.add('U');
        latin.add('V'); latin.add('W'); latin.add('X'); latin.add('Y'); latin.add('Z'); latin.add('a'); latin.add('b');
        latin.add('c'); latin.add('d'); latin.add('e'); latin.add('f'); latin.add('g'); latin.add('h'); latin.add('i');
        latin.add('j'); latin.add('k'); latin.add('l'); latin.add('m'); latin.add('n'); latin.add('o'); latin.add('p');
        latin.add('q'); latin.add('r'); latin.add('s'); latin.add('t'); latin.add('u'); latin.add('v'); latin.add('w');
        latin.add('x'); latin.add('y'); latin.add('z');

        passwordCharacters = new ArrayList<>(62);
        passwordCharacters.addAll(digits);
        passwordCharacters.addAll(latin);

        emailCharacters = new ArrayList<>(66);
        emailCharacters.addAll(digits);
        emailCharacters.addAll(latin);
        emailCharacters.add('@'); emailCharacters.add('.'); emailCharacters.add('_'); emailCharacters.add('-');
    }

    private static boolean areAllSymbolsInSet(String input, ArrayList<Character> pattern) {
        if (input == null || input.isBlank()) return true;
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (!pattern.contains(aChar)) return false;
        }
        return true;
    }

    public String acceptableEmail(String email) {
        if (email == null || email.isBlank()) return EMAIL_CANNOT_BE_EMPTY.getValue();
        if (!areAllSymbolsInSet(email, emailCharacters)) {
            return INVALID_EMAIL_CHARACTERS.getValue();
        } else {
            EmailValidator validator = EmailValidator.getInstance();
            return validator.isValid(email) ? "" : INCORRECT_EMAIL.getValue();
        }
    }

    public String acceptablePassword(String password) {
        if (password == null || password.isBlank()) return PASSWORD_CANNOT_BE_EMPTY.getValue();
        if (password.length() < MIN_PASSWORD_LENGTH)
            return "The minimum password length must be " + MIN_PASSWORD_LENGTH + " symbols";
        if (password.length() > MAX_PASSWORD_LENGTH)
            return "The maximum password length must be " + MAX_PASSWORD_LENGTH + " symbols";
        if (!areAllSymbolsInSet(password, passwordCharacters)) {
            return INVALID_PASSWORD_CHARACTERS.getValue();
        } else {
            return "";
        }
    }
}
