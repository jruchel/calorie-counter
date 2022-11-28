package com.jruchel.caloriecounter.model.api.meal;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "HH:mm:ss")
    private Date time;
}
