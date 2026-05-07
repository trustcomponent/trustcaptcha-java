package com.trustcomponent.trustcaptcha.exception;

public class ServerUnreachableException extends FailoverException {

    public ServerUnreachableException() {
        super("Could not reach the TrustCaptcha server. This is a high-trust failover signal — your backend was unable to contact our servers.");
    }
}
