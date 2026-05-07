package com.trustcomponent.trustcaptcha.exception;

public class VerificationResultExpiredException extends CaptchaFailureException {

    public VerificationResultExpiredException() {
        super("The verification result has expired and can no longer be retrieved.");
    }
}
