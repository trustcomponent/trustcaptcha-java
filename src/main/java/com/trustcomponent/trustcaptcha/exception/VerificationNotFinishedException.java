package com.trustcomponent.trustcaptcha.exception;

public class VerificationNotFinishedException extends CaptchaFailureException {

    public VerificationNotFinishedException() {
        super("The verification is not yet completed. Please wait until the user has finished solving the captcha before requesting the result.");
    }
}
