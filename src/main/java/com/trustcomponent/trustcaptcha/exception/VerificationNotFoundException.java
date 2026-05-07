package com.trustcomponent.trustcaptcha.exception;

public class VerificationNotFoundException extends CaptchaFailureException {

    public VerificationNotFoundException() {
        super("No verification could be found for the given verification token.");
    }
}
