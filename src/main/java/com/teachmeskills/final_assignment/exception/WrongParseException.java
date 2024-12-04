package com.teachmeskills.final_assignment.exception;

public class WrongParseException extends Exception{
    private int codeError;

    public WrongParseException(String message) {
        super(message);
    }

    public WrongParseException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
