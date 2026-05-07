package com.trustcomponent.trustcaptcha;

import com.trustcomponent.trustcaptcha.exception.ApiKeyInvalidException;
import com.trustcomponent.trustcaptcha.exception.ServerUnreachableException;
import com.trustcomponent.trustcaptcha.exception.VerificationNotFinishedException;
import com.trustcomponent.trustcaptcha.exception.VerificationNotFoundException;
import com.trustcomponent.trustcaptcha.exception.VerificationResultExpiredException;
import com.trustcomponent.trustcaptcha.exception.VerificationResultRetrievalLimitReachedException;
import com.trustcomponent.trustcaptcha.exception.VerificationTokenInvalidException;
import com.trustcomponent.trustcaptcha.model.VerificationResult;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrustCaptchaTest {

    String VERIFICATION_VALID = "eyJ2ZXJpZmljYXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMCJ9";
    String VERIFICATION_NOT_FOUND = "eyJ2ZXJpZmljYXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMSJ9";
    String VERIFICATION_LOCKED = "eyJ2ZXJpZmljYXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMiJ9";
    String VERIFICATION_EXPIRED = "eyJ2ZXJpZmljYXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMyJ9";
    String VERIFICATION_LIMIT_REACHED = "eyJ2ZXJpZmljYXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwNCJ9";
    String VERIFICATION_WITH_UNKNOWN_FIELDS = "eyJ2ZXJpZmljYXRpb25JZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMCIsInVua25vd25GaWVsZCI6ImZvbyIsImFub3RoZXJKdW5rIjo0MiwibmVzdGVkIjp7IngiOjF9fQ==";

    @Test
    void runGetVerificationResult() throws Exception {
        VerificationResult verificationResult = TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", VERIFICATION_VALID);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), verificationResult.getVerificationId());
    }

    @Test
    void runGetVerificationResultViaBuilder() throws Exception {
        TrustCaptcha tc = TrustCaptcha.builder("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx").build();
        VerificationResult verificationResult = tc.getVerificationResult(VERIFICATION_VALID);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), verificationResult.getVerificationId());
    }

    @Test
    void throwVerificationTokenInvalidException() {
        assertThrows(VerificationTokenInvalidException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "invalid-base64"));
    }

    @Test
    void throwVerificationTokenInvalidWhenBase64ButNotJson() {
        // base64("not-a-json")
        assertThrows(VerificationTokenInvalidException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "bm90LWEtanNvbg=="));
    }

    @Test
    void throwVerificationTokenInvalidWhenJsonMissingVerificationId() {
        // base64('{"foo":"bar"}')
        assertThrows(VerificationTokenInvalidException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "eyJmb28iOiJiYXIifQ=="));
    }

    @Test
    void throwVerificationNotFoundException() {
        assertThrows(VerificationNotFoundException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", VERIFICATION_NOT_FOUND));
    }

    @Test
    void throwApiKeyInvalidException() {
        assertThrows(ApiKeyInvalidException.class, () -> TrustCaptcha.getVerificationResult("invalid-key", VERIFICATION_VALID));
    }

    @Test
    void throwVerificationNotFinishedException() {
        assertThrows(VerificationNotFinishedException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", VERIFICATION_LOCKED));
    }

    @Test
    void throwVerificationResultExpiredException() {
        assertThrows(VerificationResultExpiredException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", VERIFICATION_EXPIRED));
    }

    @Test
    void throwVerificationResultRetrievalLimitReachedException() {
        assertThrows(VerificationResultRetrievalLimitReachedException.class, () -> TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", VERIFICATION_LIMIT_REACHED));
    }

    @Test
    void toleratesUnknownFieldsInVerificationToken() throws Exception {
        VerificationResult result = TrustCaptcha.getVerificationResult("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", VERIFICATION_WITH_UNKNOWN_FIELDS);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result.getVerificationId());
    }

    @Test
    void throwServerUnreachableExceptionOnUnreachableHost() {
        TrustCaptcha tc = TrustCaptcha.builder("ak_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .apiHost("http://localhost:1")
                .connectTimeoutMs(500)
                .readTimeoutMs(500)
                .build();
        assertThrows(ServerUnreachableException.class, () -> tc.getVerificationResult(VERIFICATION_VALID));
    }
}