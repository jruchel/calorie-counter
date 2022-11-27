package com.jruchel.caloriecounter.model.api.meal;

import jakarta.validation.constraints.Min;
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

    @Min(0)
    private Integer calories;

    @Builder.Default private Map<String, Integer> foods = new HashMap<>();
}
