package com.trustcomponent.trustcaptcha.exception;

public class VerificationNotFinishedException extends CaptchaFailureException {

    public VerificationNotFinishedException() {
        super("Verification not finished yet. Please wait and try again later.");
    }
}
