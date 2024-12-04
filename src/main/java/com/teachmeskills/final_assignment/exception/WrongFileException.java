package com.teachmeskills.final_assignment.exception;

public class WrongFileException extends Exception{

    private int codeError;

    public WrongFileException(String message) {
        super(message);
    }

    public WrongFileException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
