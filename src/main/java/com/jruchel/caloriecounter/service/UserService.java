package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.error.exceptions.FieldValueValidationException;
import com.jruchel.caloriecounter.error.exceptions.UserNotFoundException;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User> {

    private final UserRepository userRepository;

    public User createUser(final String username, final int dailyLimit)
            throws FieldValueValidationException, NoSuchFieldException, IllegalAccessException {
        verifyUsername(username);
        User user = new User(UUID.randomUUID().toString(), username, dailyLimit);
        return userRepository.insert(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(final String username) throws UserNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User changeCalorieLimit(final String username, final int newLimit)
            throws UserNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException(username));
        user.setDailyLimit(newLimit);
        return userRepository.save(user);
    }

    public void verifyUsername(final String username)
            throws FieldValueValidationException, NoSuchFieldException, IllegalAccessException {
        if (!userRepository.isUsernameUnique(username))
            throw createFieldValidationException(
                    User.builder().username(username).build(), "username", "Already exists");
    }
}
