package com.trustcomponent.trustcaptcha.exception;

public class ClientReportedServerUnreachableException extends FailoverException {

    public ClientReportedServerUnreachableException() {
        super("The client reported it could not reach the TrustCaptcha server, but the gateway has no record of a recent outage. Treat this with caution: a malicious client may be claiming a failover that did not happen.");
    }
}
