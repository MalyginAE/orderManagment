package com.nexign.config.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix ="partner-service")
@ConstructorBinding
@Validated
@Value
public class PartnerServiceProperties {
}
