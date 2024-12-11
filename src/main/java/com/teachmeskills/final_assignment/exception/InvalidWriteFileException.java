package com.teachmeskills.final_assignment.exception;

public class InvalidWriteFileException extends Exception {
    private int codeError;

    public InvalidWriteFileException(String message) {
        super(message);
    }

    public InvalidWriteFileException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
