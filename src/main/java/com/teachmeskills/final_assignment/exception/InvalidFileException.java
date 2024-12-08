package com.teachmeskills.final_assignment.exception;

public class InvalidFileException extends Exception{

    private int codeError;

    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
