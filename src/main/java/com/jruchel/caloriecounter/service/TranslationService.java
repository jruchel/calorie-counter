package com.jruchel.caloriecounter.service;

import com.deepl.api.DeepLException;
import com.deepl.api.Language;
import com.deepl.api.Translator;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final Translator translator;

    public List<Language> getAvailableLanguages() throws InterruptedException, DeepLException {
        return translator.getSourceLanguages();
    }

    public Language isLanguageAvailable(String language)
            throws DeepLException, InterruptedException {
        final String originalInput = language;
        // Lower case, remove whitespace
        language = language.toLowerCase(Locale.ROOT);
        language = language.replaceAll("\\s+", "");
        final String code = language;
        // capitalize first letter
        language = language.substring(0, 1).toUpperCase() + language.substring(1);
        final String name = language;
        return getAvailableLanguages().stream()
                .filter(l -> l.getCode().equals(code) || l.getName().equals(name))
                .findFirst()
                .orElseThrow(
                        () ->
                                new NullPointerException(
                                        "Language '%s' not found".formatted(originalInput)));
    }

    public String translate(String sourceText, String targetLanguageCode)
            throws InterruptedException, DeepLException {
        try {
            return translator.translateText(sourceText, null, targetLanguageCode).getText();
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new DeepLException(illegalArgumentException.getMessage());
        }
    }
}
