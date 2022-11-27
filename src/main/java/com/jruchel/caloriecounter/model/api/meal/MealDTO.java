package com.jruchel.caloriecounter.model.api.meal;

import jakarta.validation.constraints.Min;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealDTO {

    private String name;

    @Min(1)
    private int calories;

    @Builder.Default private Map<String, Integer> foods = new HashMap<>();
    private Date date;
}
