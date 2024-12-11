package com.teachmeskills.final_assignment.exception;

public class InvalidDirectoryException extends Exception {
    private int codeError;

    public InvalidDirectoryException(String message) {
        super(message);
    }

    public InvalidDirectoryException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
