package com.jruchel.caloriecounter.mapper;

import com.deepl.api.Language;
import com.jruchel.caloriecounter.model.api.LanguageDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface LanguageMapper {

    LanguageDTO toDTO(Language language);

    List<LanguageDTO> toDTOList(List<Language> languages);
}
