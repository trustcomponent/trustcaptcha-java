package com.trustcomponent.trustcaptcha.exception;

public class VerificationNotFoundException extends CaptchaFailureException {

    public VerificationNotFoundException() {
        super("The verification result could not be found. It might be that the result was already retrieved earlier.");
    }
}
