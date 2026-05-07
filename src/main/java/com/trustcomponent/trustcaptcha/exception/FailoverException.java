package com.trustcomponent.trustcaptcha.exception;

public abstract class FailoverException extends CaptchaFailureException {

    public FailoverException(String message) {
        super(message);
    }
}
