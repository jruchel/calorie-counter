package com.jruchel.caloriecounter.model.api.meal;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
