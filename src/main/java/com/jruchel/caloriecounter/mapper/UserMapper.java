package com.jruchel.caloriecounter.mapper;

import com.jruchel.caloriecounter.model.api.user.UserCreationResponse;
import com.jruchel.caloriecounter.model.api.user.UserDTO;
import com.jruchel.caloriecounter.model.internal.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserCreationResponse toCreationResponse(User user);

    UserDTO toDTO(User user);
}
