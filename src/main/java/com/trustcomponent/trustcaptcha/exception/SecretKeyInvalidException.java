package com.trustcomponent.trustcaptcha.exception;

public class SecretKeyInvalidException extends CaptchaFailureException {

    public SecretKeyInvalidException() {
        super("Error while processing the secret key. Please check if the secret key is correct.");
    }
}
