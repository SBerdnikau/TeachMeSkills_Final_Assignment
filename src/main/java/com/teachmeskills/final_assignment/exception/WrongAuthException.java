package com.teachmeskills.final_assignment.exception;

public class WrongAuthException extends Exception{
    private int errorCode;

    public WrongAuthException(String message) {
        super(message);
    }

    public WrongAuthException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
