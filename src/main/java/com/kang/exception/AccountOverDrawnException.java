package com.kang.exception;

public class AccountOverDrawnException extends RuntimeException {
    private String message;

    public AccountOverDrawnException(String message) {
        super(message);
        this.message = message;
    }

    public AccountOverDrawnException() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
