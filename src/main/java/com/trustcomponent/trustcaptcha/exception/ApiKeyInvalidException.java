package com.trustcomponent.trustcaptcha.exception;

public class ApiKeyInvalidException extends CaptchaFailureException {

    public ApiKeyInvalidException() {
        super("The provided api key is invalid. Please verify the api key from your captcha settings.");
    }
}
