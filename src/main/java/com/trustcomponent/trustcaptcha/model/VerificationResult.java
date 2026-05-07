package com.trustcomponent.trustcaptcha.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationResult {
    @JsonProperty("captchaId")
    @NotNull
    private final UUID captchaId;

    @JsonProperty("verificationId")
    @NotNull
    private final UUID verificationId;

    @JsonProperty("verificationPassed")
    private final boolean verificationPassed;

    @JsonProperty("score")
    private final double score;

    @JsonProperty("decisionType")
    @NotNull
    private final String decisionType;

    @JsonProperty("decisionAction")
    @NotNull
    private final String decisionAction;

    @JsonProperty("gatewayFailoverActive")
    private final boolean gatewayFailoverActive;

    @JsonProperty("riskScoringEnabled")
    private final boolean riskScoringEnabled;

    @JsonProperty("minimalDataModeEnabled")
    private final boolean minimalDataModeEnabled;

    @JsonProperty("origin")
    @NotNull
    private final String origin;

    @JsonProperty("ipAddress")
    @NotNull
    private final String ipAddress;

    @JsonProperty("countryCode")
    @NotNull
    private final String countryCode;

    @JsonProperty("deviceFamily")
    @NotNull
    private final String deviceFamily;

    @JsonProperty("operatingSystem")
    @NotNull
    private final String operatingSystem;

    @JsonProperty("browser")
    @NotNull
    private final String browser;

    @JsonProperty("verificationStartedAt")
    @NotNull
    private final String verificationStartedAt;

    @JsonProperty("verificationFinishedAt")
    @NotNull
    private final String verificationFinishedAt;

    @JsonProperty("resultExpiresAt")
    @NotNull
    private final String resultExpiresAt;

    @JsonProperty("resultFirstFetchedAt")
    @NotNull
    private final String resultFirstFetchedAt;

    @JsonProperty("resultLastFetchedAt")
    @NotNull
    private final String resultLastFetchedAt;

    public VerificationResult(
            @JsonProperty("captchaId") @NotNull UUID captchaId,
            @JsonProperty("verificationId") @NotNull UUID verificationId,
            @JsonProperty("verificationPassed") boolean verificationPassed,
            @JsonProperty("score") double score,
            @JsonProperty("decisionType") @NotNull String decisionType,
            @JsonProperty("decisionAction") @NotNull String decisionAction,
            @JsonProperty("gatewayFailoverActive") boolean gatewayFailoverActive,
            @JsonProperty("riskScoringEnabled") boolean riskScoringEnabled,
            @JsonProperty("minimalDataModeEnabled") boolean minimalDataModeEnabled,
            @JsonProperty("origin") @NotNull String origin,
            @JsonProperty("ipAddress") @NotNull String ipAddress,
            @JsonProperty("countryCode") @NotNull String countryCode,
            @JsonProperty("deviceFamily") @NotNull String deviceFamily,
            @JsonProperty("operatingSystem") @NotNull String operatingSystem,
            @JsonProperty("browser") @NotNull String browser,
            @JsonProperty("verificationStartedAt") @NotNull String verificationStartedAt,
            @JsonProperty("verificationFinishedAt") @NotNull String verificationFinishedAt,
            @JsonProperty("resultExpiresAt") @NotNull String resultExpiresAt,
            @JsonProperty("resultFirstFetchedAt") @NotNull String resultFirstFetchedAt,
            @JsonProperty("resultLastFetchedAt") @NotNull String resultLastFetchedAt
    ) {
        this.captchaId = captchaId;
        this.verificationId = verificationId;
        this.verificationPassed = verificationPassed;
        this.score = score;
        this.decisionType = decisionType;
        this.decisionAction = decisionAction;
        this.gatewayFailoverActive = gatewayFailoverActive;
        this.riskScoringEnabled = riskScoringEnabled;
        this.minimalDataModeEnabled = minimalDataModeEnabled;
        this.origin = origin;
        this.ipAddress = ipAddress;
        this.countryCode = countryCode;
        this.deviceFamily = deviceFamily;
        this.operatingSystem = operatingSystem;
        this.browser = browser;
        this.verificationStartedAt = verificationStartedAt;
        this.verificationFinishedAt = verificationFinishedAt;
        this.resultExpiresAt = resultExpiresAt;
        this.resultFirstFetchedAt = resultFirstFetchedAt;
        this.resultLastFetchedAt = resultLastFetchedAt;
    }

    @NotNull public final UUID getCaptchaId() { return captchaId; }
    @NotNull public final UUID getVerificationId() { return verificationId; }
    public final boolean isVerificationPassed() { return verificationPassed; }
    public final double getScore() { return score; }
    @NotNull public final String getDecisionType() { return decisionType; }
    @NotNull public final String getDecisionAction() { return decisionAction; }
    public final boolean isGatewayFailoverActive() { return gatewayFailoverActive; }
    public final boolean isRiskScoringEnabled() { return riskScoringEnabled; }
    public final boolean isMinimalDataModeEnabled() { return minimalDataModeEnabled; }
    @NotNull public final String getOrigin() { return origin; }
    @NotNull public final String getIpAddress() { return ipAddress; }
    @NotNull public final String getCountryCode() { return countryCode; }
    @NotNull public final String getDeviceFamily() { return deviceFamily; }
    @NotNull public final String getOperatingSystem() { return operatingSystem; }
    @NotNull public final String getBrowser() { return browser; }
    @NotNull public final String getVerificationStartedAt() { return verificationStartedAt; }
    @NotNull public final String getVerificationFinishedAt() { return verificationFinishedAt; }
    @NotNull public final String getResultExpiresAt() { return resultExpiresAt; }
    @NotNull public final String getResultFirstFetchedAt() { return resultFirstFetchedAt; }
    @NotNull public final String getResultLastFetchedAt() { return resultLastFetchedAt; }
}
