package com.trustcomponent.trustcaptcha;

import com.trustcomponent.trustcaptcha.exception.SecretKeyInvalidException;
import com.trustcomponent.trustcaptcha.exception.VerificationNotFinishedException;
import com.trustcomponent.trustcaptcha.exception.VerificationNotFoundException;
import com.trustcomponent.trustcaptcha.exception.VerificationTokenInvalidException;
import com.trustcomponent.trustcaptcha.model.VerificationResult;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CaptchaManagerTest {

    String VERIFICATION_VALID = "eyJhcGlFbmRwb2ludCI6Imh0dHBzOi8vYXBpLnRydXN0Y29tcG9uZW50LmNvbSIsInZlcmlmaWNhdGlvbklkIjoiMDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAwIiwiZW5jcnlwdGVkQWNjZXNzVG9rZW4iOiJ0b2tlbiJ9";
    String VERIFICATION_NOT_FOUND = "eyJhcGlFbmRwb2ludCI6Imh0dHBzOi8vYXBpLnRydXN0Y29tcG9uZW50LmNvbSIsInZlcmlmaWNhdGlvbklkIjoiMDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAxIiwiZW5jcnlwdGVkQWNjZXNzVG9rZW4iOiJ0b2tlbiJ9";
    String VERIFICATION_LOCKED = "eyJhcGlFbmRwb2ludCI6Imh0dHBzOi8vYXBpLnRydXN0Y29tcG9uZW50LmNvbSIsInZlcmlmaWNhdGlvbklkIjoiMDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAyIiwiZW5jcnlwdGVkQWNjZXNzVG9rZW4iOiJ0b2tlbiJ9";

    @Test
    void runGetVerificationResult() throws Exception {
        VerificationResult verificationResult = CaptchaManager.getVerificationResult("secret-key", VERIFICATION_VALID);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), verificationResult.getVerificationId());
    }

    @Test
    void throwVerificationTokenInvalidException() {
        assertThrows(VerificationTokenInvalidException.class, () -> CaptchaManager.getVerificationResult("", ""));
    }

    @Test
    void throwVerificationNotFoundException() {
        assertThrows(VerificationNotFoundException.class, () -> CaptchaManager.getVerificationResult("secret-key", VERIFICATION_NOT_FOUND));
    }

    @Test
    void throwSecretKeyInvalidException() {
        assertThrows(SecretKeyInvalidException.class, () -> CaptchaManager.getVerificationResult("invalid-key", VERIFICATION_VALID));
    }

    @Test
    void throwVerificationNotFinishedException() {
        assertThrows(VerificationNotFinishedException.class, () -> CaptchaManager.getVerificationResult("secret-key", VERIFICATION_LOCKED));
    }
}