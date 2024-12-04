package com.teachmeskills.final_assignment.exception;

public class WrongLoggerException extends RuntimeException {

    private int codeError;

    public WrongLoggerException(String message) {
        super(message);
    }

    public WrongLoggerException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
