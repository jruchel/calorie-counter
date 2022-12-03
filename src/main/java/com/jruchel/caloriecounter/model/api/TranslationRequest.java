package com.jruchel.caloriecounter.model.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslationRequest {

    private String text;
    private String targetLanguage;
}
