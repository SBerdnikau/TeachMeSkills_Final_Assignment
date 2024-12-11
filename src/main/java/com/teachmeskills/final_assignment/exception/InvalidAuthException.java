package com.teachmeskills.final_assignment.exception;

public class InvalidAuthException extends Exception {
    private int errorCode;

    public InvalidAuthException(String message) {
        super(message);
    }

    public InvalidAuthException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
