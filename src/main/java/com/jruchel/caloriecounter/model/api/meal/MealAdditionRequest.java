package com.jruchel.caloriecounter.model.api.meal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealAdditionRequest {

    private String username;
    private String name;
    private Integer calories;
}
