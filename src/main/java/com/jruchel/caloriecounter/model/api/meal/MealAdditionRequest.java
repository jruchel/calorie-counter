package com.jruchel.caloriecounter.model.api.meal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealAdditionRequest {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Builder.Default private Map<String, Integer> foods = new HashMap<>();
}
