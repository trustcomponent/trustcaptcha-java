package com.trustcomponent.trustcaptcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustcomponent.trustcaptcha.exception.*;
import com.trustcomponent.trustcaptcha.model.VerificationResult;
import com.trustcomponent.trustcaptcha.model.VerificationToken;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class CaptchaManager {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static VerificationResult getVerificationResult(String secretKey, String base64verificationToken) throws CaptchaFailureException {

        return getVerificationResult(secretKey, base64verificationToken, "");
    }

    public static VerificationResult getVerificationResult(String secretKey, String base64verificationToken, String platform) throws CaptchaFailureException {

        VerificationToken verificationToken = getVerificationToken(base64verificationToken);
        String urlAsString = verificationToken.getApiEndpoint() + "/verifications/" + verificationToken.getVerificationId() + "/assessments";

        try {
            URL url = new URL(urlAsString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("tc-authorization", secretKey);
            connection.setRequestProperty("tc-library-language", "jvm");
            connection.setRequestProperty("tc-library-platform", platform);
            connection.setRequestProperty("tc-library-version", "2.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == 403) {
                throw new SecretKeyInvalidException();
            } else if (responseCode == 404) {
                throw new VerificationNotFoundException();
            } else if (responseCode == 423) {
                throw new VerificationNotFinishedException();
            }

            return mapper.readValue(connection.getInputStream(), VerificationResult.class);

        } catch (FileNotFoundException exception) {
            throw new VerificationNotFoundException();
        } catch (VerificationNotFoundException | SecretKeyInvalidException | VerificationNotFinishedException exception) {
            throw exception;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve verification result from " + urlAsString, e);
        }
    }

    private static VerificationToken getVerificationToken(String verificationToken) throws VerificationTokenInvalidException {

        String decodedVerificationToken = new String(Base64.getDecoder().decode(verificationToken));

        try {
            return mapper.readValue(decodedVerificationToken, VerificationToken.class);
        } catch (Exception e) {
            throw new VerificationTokenInvalidException();
        }
    }
}
