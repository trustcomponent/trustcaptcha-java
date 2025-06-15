package com.trustcomponent.trustcaptcha.exception;

public class VerificationTokenInvalidException extends CaptchaFailureException {

    public VerificationTokenInvalidException() {
        super("The verification token in invalid.");
    }
}
