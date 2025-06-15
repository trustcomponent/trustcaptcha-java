package com.trustcomponent.trustcaptcha.exception;

public abstract class CaptchaFailureException extends Exception {

    public CaptchaFailureException(String message) {
        super(message);
    }
}
