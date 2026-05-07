package com.trustcomponent.trustcaptcha.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationToken {

    @JsonProperty("verificationId")
    @NotNull
    private final UUID verificationId;

    @JsonProperty("clientFailover")
    private final boolean clientFailover;

    public VerificationToken(@JsonProperty("verificationId") @NotNull UUID verificationId,
                             @JsonProperty("clientFailover") boolean clientFailover) {
        this.verificationId = verificationId;
        this.clientFailover = clientFailover;
    }

    @NotNull
    public final UUID getVerificationId() {
        return this.verificationId;
    }

    public final boolean isClientFailover() {
        return this.clientFailover;
    }
}
