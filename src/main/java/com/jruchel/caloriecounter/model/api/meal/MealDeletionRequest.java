package com.jruchel.caloriecounter.model.api.meal;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealDeletionRequest {

    private String name;
    private Date date;
}
