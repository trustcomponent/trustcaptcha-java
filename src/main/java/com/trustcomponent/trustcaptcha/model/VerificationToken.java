package com.trustcomponent.trustcaptcha.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationToken {

    @JsonProperty("apiEndpoint")
    @NotNull
    private final String apiEndpoint;

    @JsonProperty("verificationId")
    @NotNull
    private final UUID verificationId;

    public VerificationToken(
            @JsonProperty("apiEndpoint") @NotNull String apiEndpoint,
            @JsonProperty("verificationId") @NotNull UUID verificationId
    ) {
        this.apiEndpoint = apiEndpoint;
        this.verificationId = verificationId;
    }

    @NotNull
    public String getApiEndpoint() {
        return this.apiEndpoint;
    }

    @NotNull
    public final UUID getVerificationId() {
        return this.verificationId;
    }
}
