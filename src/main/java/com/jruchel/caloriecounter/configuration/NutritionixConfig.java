package com.jruchel.caloriecounter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("nutritionix.api")
@Getter
@Setter
public class NutritionixConfig {

    private String appKey;
    private String appId;
    private String appAddress;
}
