package com.teachmeskills.final_assignment.exception;

public class InvalidPasswordException extends Exception{

    private int errorCode;

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
