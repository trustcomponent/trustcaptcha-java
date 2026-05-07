package com.trustcomponent.trustcaptcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustcomponent.trustcaptcha.exception.*;
import com.trustcomponent.trustcaptcha.model.VerificationResult;
import com.trustcomponent.trustcaptcha.model.VerificationToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TrustCaptcha {

    private static final String LIBRARY_VERSION = "3.0.0";
    private static final String LIBRARY_LANGUAGE = "jvm";
    private static final String DEFAULT_API_HOST = "https://api.trustcomponent.com";
    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 3000;
    private static final int DEFAULT_READ_TIMEOUT_MS = 5000;

    private static final ObjectMapper mapper = new ObjectMapper();

    private final String apiKey;
    private final String apiHost;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;
    private final Proxy proxy;

    private TrustCaptcha(Builder b) {
        if (b.apiKey == null || b.apiKey.isEmpty()) {
            throw new IllegalArgumentException("apiKey must not be null or empty");
        }
        this.apiKey = b.apiKey;
        this.apiHost = b.apiHost;
        this.connectTimeoutMs = b.connectTimeoutMs;
        this.readTimeoutMs = b.readTimeoutMs;
        this.proxy = b.proxy;
    }

    public static Builder builder(String apiKey) {
        return new Builder(apiKey);
    }

    public static VerificationResult getVerificationResult(String apiKey, String base64verificationToken) throws CaptchaFailureException {
        return new Builder(apiKey).build().getVerificationResult(base64verificationToken);
    }

    public VerificationResult getVerificationResult(String base64verificationToken) throws CaptchaFailureException {

        VerificationToken verificationToken = parseVerificationToken(base64verificationToken);
        String urlAsString = apiHost + "/v2/verifications/" + verificationToken.getVerificationId() + "/results"
                + (verificationToken.isClientFailover() ? "?clientFailover=true" : "");

        HttpURLConnection connection;
        int responseCode;
        try {
            URL url = new URL(urlAsString);
            connection = (HttpURLConnection) (proxy != null ? url.openConnection(proxy) : url.openConnection());
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("User-Agent", buildUserAgent());
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(connectTimeoutMs);
            connection.setReadTimeout(readTimeoutMs);
            responseCode = connection.getResponseCode();
        } catch (ConnectException | SocketTimeoutException | UnknownHostException | NoRouteToHostException e) {
            throw new ServerUnreachableException();
        } catch (IOException e) {
            // Generic IO (e.g. DNS / TLS-handshake failure) → treat as unreachable.
            throw new ServerUnreachableException();
        }

        try {
            if (responseCode == 403) {
                throw new ApiKeyInvalidException();
            } else if (responseCode == 404) {
                throw new VerificationNotFoundException();
            } else if (responseCode == 410) {
                throw new VerificationResultExpiredException();
            } else if (responseCode == 412) {
                throw new ClientReportedServerUnreachableException();
            } else if (responseCode == 423) {
                throw new VerificationNotFinishedException();
            } else if (responseCode == 429) {
                throw new VerificationResultRetrievalLimitReachedException();
            }

            return mapper.readValue(connection.getInputStream(), VerificationResult.class);

        } catch (FileNotFoundException exception) {
            throw new VerificationNotFoundException();
        } catch (CaptchaFailureException exception) {
            throw exception;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve verification result from " + urlAsString, e);
        }
    }

    private static VerificationToken parseVerificationToken(String verificationToken) throws VerificationTokenInvalidException {
        VerificationToken token;
        try {
            String decoded = new String(Base64.getDecoder().decode(verificationToken));
            token = mapper.readValue(decoded, VerificationToken.class);
        } catch (Exception e) {
            throw new VerificationTokenInvalidException();
        }
        if (token == null || token.getVerificationId() == null) {
            throw new VerificationTokenInvalidException();
        }
        return token;
    }

    private static String buildUserAgent() {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("language", LIBRARY_LANGUAGE);
        payload.put("version", LIBRARY_VERSION);
        try {
            String json = mapper.writeValueAsString(payload);
            String encoded = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
            return "Trustcaptcha/" + encoded;
        } catch (Exception e) {
            return "Trustcaptcha/";
        }
    }

    public static final class Builder {
        private final String apiKey;
        private String apiHost = DEFAULT_API_HOST;
        private int connectTimeoutMs = DEFAULT_CONNECT_TIMEOUT_MS;
        private int readTimeoutMs = DEFAULT_READ_TIMEOUT_MS;
        private Proxy proxy = null;

        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder apiHost(String apiHost) {
            this.apiHost = apiHost;
            return this;
        }

        public Builder connectTimeoutMs(int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
            return this;
        }

        public Builder readTimeoutMs(int readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
            return this;
        }

        public Builder proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder proxy(String host, int port) {
            this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            return this;
        }

        public TrustCaptcha build() {
            return new TrustCaptcha(this);
        }
    }
}
