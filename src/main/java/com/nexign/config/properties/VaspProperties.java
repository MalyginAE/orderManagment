package com.nexign.config.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@ConfigurationProperties(prefix ="vasp")
@ConstructorBinding
@Validated
@Value
public class VaspProperties {
    String domain ="localhost:3000";

}
