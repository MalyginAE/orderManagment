package com.nexign.config.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix ="promocode-inventory")
@ConstructorBinding
@Validated
@Value
public class PromoCodeInventoryProperties {
    String domain = "localhost:3000";
}
