package com.jruchel.caloriecounter.controller;

import com.deepl.api.DeepLException;
import com.jruchel.caloriecounter.mapper.LanguageMapper;
import com.jruchel.caloriecounter.model.api.LanguageDTO;
import com.jruchel.caloriecounter.model.api.TranslationRequest;
import com.jruchel.caloriecounter.service.TranslationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {

    private final TranslationService translationService;
    private final LanguageMapper languageMapper;

    @GetMapping("/available/all")
    public ResponseEntity<List<LanguageDTO>> getAvailableLanguages()
            throws DeepLException, InterruptedException {
        return ResponseEntity.ok(
                languageMapper.toDTOList(translationService.getAvailableLanguages()));
    }

    @GetMapping("/available")
    public ResponseEntity<LanguageDTO> isLanguageAvailable(@RequestParam String language)
            throws InterruptedException, DeepLException {
        return ResponseEntity.ok(
                languageMapper.toDTO(translationService.isLanguageAvailable(language)));
    }

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody TranslationRequest translationRequest)
            throws DeepLException, InterruptedException {
        return ResponseEntity.ok(
                translationService.translate(
                        translationRequest.getText(), translationRequest.getTargetLanguage()));
    }
}
