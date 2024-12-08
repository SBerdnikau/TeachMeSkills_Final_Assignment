package com.teachmeskills.final_assignment.exception;

public class InvalidParseDocumentException extends Exception{
    private int codeError;

    public InvalidParseDocumentException(String message) {
        super(message);
    }

    public InvalidParseDocumentException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
