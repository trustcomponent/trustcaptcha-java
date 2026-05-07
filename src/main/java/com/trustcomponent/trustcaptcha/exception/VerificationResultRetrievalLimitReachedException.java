package com.trustcomponent.trustcaptcha.exception;

public class VerificationResultRetrievalLimitReachedException extends CaptchaFailureException {

    public VerificationResultRetrievalLimitReachedException() {
        super("The verification result has reached its maximum retrieval count and can no longer be retrieved.");
    }
}
