package com.jruchel.caloriecounter.configuration;

import com.deepl.api.Translator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("deepl")
@Getter
@Setter
public class DeepLConfig {

    private String appKey;

    @Bean
    public Translator translator() {
        return new Translator(appKey);
    }
}
