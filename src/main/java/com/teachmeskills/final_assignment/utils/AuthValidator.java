package com.teachmeskills.final_assignment.utils;

import com.teachmeskills.final_assignment.exception.InvalidLoginException;
import com.teachmeskills.final_assignment.exception.InvalidPasswordException;

/**
 * This class checks the login and password for validity when entered by the user.
 */
public class AuthValidator {

    public static boolean isValidLogin(String login) throws InvalidLoginException {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException(Constants.MESSAGE_LOGIN_IS_EMPTY);
        }
        return true;
    }

    public static boolean isValidPassword(String password) throws InvalidPasswordException {
        if (password == null || password.isEmpty()) {
           throw new InvalidPasswordException(Constants.MESSAGE_PASSWORD_IS_EMPTY);

        }
        if (password.length() < 6) {
            throw new InvalidPasswordException(Constants.MESSAGE_LOGIN_TOO_SHORT);
        }
        return true;
    }
}