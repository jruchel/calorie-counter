package com.jruchel.caloriecounter.model.api.meal;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealDTO {

    private String name;
    private int calories;
    private Date date;
}
