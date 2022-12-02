package com.jruchel.caloriecounter.model.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutritionixFoodsData {

    @JsonProperty("nf_calories")
    private double calories;
}
