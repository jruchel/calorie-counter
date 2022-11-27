package com.jruchel.caloriecounter.model.internal;

import java.util.Date;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meal {

    @Id private String id;
    private String userId;
    private String name;
    private int calories;
    private Date date;
}
