package com.nexign.config.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix ="bss")
@ConstructorBinding
@Validated
@Value
public class BssProperties {
    String amqpCallback ="";
    Integer waitTimeout = 300;
    String domain = "localhost:3000";
}
