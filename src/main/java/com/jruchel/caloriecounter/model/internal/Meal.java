package com.jruchel.caloriecounter.model.internal;

import java.util.*;
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

    @Builder.Default @Id private String id = UUID.randomUUID().toString();
    private String userId;
    private String name;
    private int calories;
    @Builder.Default private Map<String, Integer> foods = new HashMap<>();
    private Date time;
}
