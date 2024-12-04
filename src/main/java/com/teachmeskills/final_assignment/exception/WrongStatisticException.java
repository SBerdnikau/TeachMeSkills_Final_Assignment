package com.teachmeskills.final_assignment.exception;

public class WrongStatisticException extends Exception{
    private int codeError;

    public WrongStatisticException(String message) {
        super(message);
    }

    public WrongStatisticException(String message, int codeError) {
        super(message);
        this.codeError = codeError;
    }

    public int getCodeError() {
        return codeError;
    }
}
