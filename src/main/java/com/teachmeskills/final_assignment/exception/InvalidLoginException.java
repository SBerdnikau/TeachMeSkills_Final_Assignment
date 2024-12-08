package com.teachmeskills.final_assignment.exception;

public class InvalidLoginException extends Exception{

    private int errorCode;

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
