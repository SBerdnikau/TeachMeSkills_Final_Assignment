package com.teachmeskills.final_assignment.exception;

public class InvalidWriteLoggerException extends RuntimeException {

    private int codeError;

    public InvalidWriteLoggerException(String message) {
        super(message);
    }

    public InvalidWriteLoggerException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
