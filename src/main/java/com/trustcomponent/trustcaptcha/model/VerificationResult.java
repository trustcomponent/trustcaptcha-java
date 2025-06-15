package com.trustcomponent.trustcaptcha.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class VerificationResult {
    @JsonProperty("captchaId")
    @NotNull
    private final UUID captchaId;

    @JsonProperty("verificationId")
    @NotNull
    private final UUID verificationId;

    @JsonProperty("score")
    private final double score;

    @JsonProperty("reason")
    @NotNull
    private final String reason;

    @JsonProperty("mode")
    @NotNull
    private final String mode;

    @JsonProperty("origin")
    @NotNull
    private final String origin;

    @JsonProperty("ipAddress")
    @NotNull
    private final String ipAddress;

    @JsonProperty("deviceFamily")
    @NotNull
    private final String deviceFamily;

    @JsonProperty("operatingSystem")
    @NotNull
    private final String operatingSystem;

    @JsonProperty("browser")
    @NotNull
    private final String browser;

    @JsonProperty("creationTimestamp")
    @NotNull
    private final String creationTimestamp;

    @JsonProperty("releaseTimestamp")
    @NotNull
    private final String releaseTimestamp;

    @JsonProperty("retrievalTimestamp")
    @NotNull
    private final String retrievalTimestamp;

    @JsonProperty("verificationPassed")
    private final boolean verificationPassed;


    public VerificationResult(
            @JsonProperty("captchaId") @NotNull UUID captchaId,
            @JsonProperty("verificationId") @NotNull UUID verificationId,
            @JsonProperty("score") double score,
            @JsonProperty("reason") @NotNull String reason,
            @JsonProperty("mode") @NotNull String mode,
            @JsonProperty("origin") @NotNull String origin,
            @JsonProperty("ipAddress") @NotNull String ipAddress,
            @JsonProperty("deviceFamily") @NotNull String deviceFamily,
            @JsonProperty("operatingSystem") @NotNull String operatingSystem,
            @JsonProperty("browser") @NotNull String browser,
            @JsonProperty("creationTimestamp") @NotNull String creationTimestamp,
            @JsonProperty("releaseTimestamp") @NotNull String releaseTimestamp,
            @JsonProperty("retrievalTimestamp") @NotNull String retrievalTimestamp,
            @JsonProperty("verificationPassed") boolean verificationPassed
    ) {
        this.captchaId = captchaId;
        this.verificationId = verificationId;
        this.score = score;
        this.reason = reason;
        this.mode = mode;
        this.origin = origin;
        this.ipAddress = ipAddress;
        this.deviceFamily = deviceFamily;
        this.operatingSystem = operatingSystem;
        this.browser = browser;
        this.creationTimestamp = creationTimestamp;
        this.releaseTimestamp = releaseTimestamp;
        this.retrievalTimestamp = retrievalTimestamp;
        this.verificationPassed = verificationPassed;
    }

    @NotNull
    public final UUID getCaptchaId() {
        return this.captchaId;
    }

    @NotNull
    public final UUID getVerificationId() {
        return this.verificationId;
    }

    public final double getScore() {
        return this.score;
    }

    @NotNull
    public final String getReason() {
        return this.reason;
    }

    @NotNull
    public final String getMode() {
        return this.mode;
    }

    @NotNull
    public final String getOrigin() {
        return this.origin;
    }

    @NotNull
    public final String getIpAddress() {
        return this.ipAddress;
    }

    @NotNull
    public final String getDeviceFamily() {
        return this.deviceFamily;
    }

    @NotNull
    public final String getOperatingSystem() {
        return this.operatingSystem;
    }

    @NotNull
    public final String getBrowser() {
        return this.browser;
    }

    @NotNull
    public final String getCreationTimestamp() {
        return this.creationTimestamp;
    }

    @NotNull
    public final String getReleaseTimestamp() {
        return this.releaseTimestamp;
    }

    @NotNull
    public final String getRetrievalTimestamp() {
        return this.retrievalTimestamp;
    }

    public boolean isVerificationPassed() {
        return verificationPassed;
    }
}

