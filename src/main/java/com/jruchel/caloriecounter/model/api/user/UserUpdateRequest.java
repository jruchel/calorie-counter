package com.jruchel.caloriecounter.model.api.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    private String username;
    private int dailyLimit;
}
