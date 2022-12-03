package com.jruchel.caloriecounter.controller;

import com.jruchel.caloriecounter.error.exceptions.FieldValueValidationException;
import com.jruchel.caloriecounter.error.exceptions.UserNotFoundException;
import com.jruchel.caloriecounter.mapper.UserMapper;
import com.jruchel.caloriecounter.model.api.user.UserCreationRequest;
import com.jruchel.caloriecounter.model.api.user.UserCreationResponse;
import com.jruchel.caloriecounter.model.api.user.UserDTO;
import com.jruchel.caloriecounter.model.api.user.UserUpdateRequest;
import com.jruchel.caloriecounter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserCreationResponse> createUser(
            @RequestBody UserCreationRequest userCreationRequest)
            throws FieldValueValidationException, IllegalAccessException, NoSuchFieldException {
        UserCreationResponse responseBody =
                userMapper.toCreationResponse(
                        userService.createUser(
                                userCreationRequest.getUsername(),
                                userCreationRequest.getDailyLimit()));
        return ResponseEntity.status(201).body(responseBody);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username)
            throws UserNotFoundException {
        UserDTO responseBody = userMapper.toDTO(userService.findByUsername(username));
        return ResponseEntity.ok(responseBody);
    }

    @PatchMapping("/limit")
    public ResponseEntity<UserDTO> changeCalorieLimit(
            @RequestBody UserUpdateRequest userUpdateRequest) throws UserNotFoundException {
        UserDTO responseBody =
                userMapper.toDTO(
                        userService.changeCalorieLimit(
                                userUpdateRequest.getUsername(),
                                userUpdateRequest.getDailyLimit()));

        return ResponseEntity.ok(responseBody);
    }
}
