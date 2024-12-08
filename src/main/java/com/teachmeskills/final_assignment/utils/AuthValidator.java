package com.teachmeskills.final_assignment.utils;

import com.teachmeskills.final_assignment.exception.InvalidLoginException;
import com.teachmeskills.final_assignment.exception.InvalidPasswordException;

public class AuthValidator {

    public static boolean isValidLogin(String login) throws InvalidLoginException {

        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException("Login must not be empty. Try again...");
        }
        return true;
    }

    public static boolean isValidPassword(String password) throws InvalidPasswordException {
        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException("Password must not be empty. Try again...");
        }
        if (password.length() < 6) {
            throw new InvalidPasswordException("Password is too short. Try again...");
        }
        return true;
    }

}
