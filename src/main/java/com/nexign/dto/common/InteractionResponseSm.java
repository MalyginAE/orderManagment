package com.nexign.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


import java.time.ZonedDateTime;

/**
 * Скриптовая модель для передачи асинхронных ответов (callbacks) при активации
 */
@Value
@Builder(builderClassName = "Builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InteractionResponseSm {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private InteractionResponseSm(
            @JsonProperty("correlationId") @NonNull String correlationId,
            @JsonProperty("step") @NonNull String step,
            @JsonProperty("stepStatus") @NonNull String stepStatus,
            @JsonProperty("responseTime") @NonNull ZonedDateTime responseTime,
            @JsonProperty("registrationTime") @NonNull ZonedDateTime registrationTime,
            @JsonProperty("body") Object body) {
        this.correlationId = correlationId;
        this.step = step;
        this.stepStatus = stepStatus;
        this.responseTime = responseTime;
        this.registrationTime = registrationTime;
        this.body = body;
    }

    @NonNull
    String correlationId;
    @NonNull
    String step;
    @NonNull
    String stepStatus;
    @NonNull
    ZonedDateTime responseTime;
    @NonNull
    ZonedDateTime registrationTime;

    Object body;
}