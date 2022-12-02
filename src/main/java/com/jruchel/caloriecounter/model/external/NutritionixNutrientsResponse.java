package com.jruchel.caloriecounter.model.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutritionixNutrientsResponse {

    @JsonProperty("foods")
    private List<NutritionixFoodsData> foods = new ArrayList<>();
}
