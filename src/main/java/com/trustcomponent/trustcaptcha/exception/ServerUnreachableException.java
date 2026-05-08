package com.trustcomponent.trustcaptcha.exception;

public class ServerUnreachableException extends FailoverException {

    public ServerUnreachableException() {
        super("Could not reach the TrustCaptcha server. Please check your network connection and consider implementing a failover mechanism.");
    }
}
