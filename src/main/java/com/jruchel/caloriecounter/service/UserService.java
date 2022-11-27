package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.error.FieldValueValidationException;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.UserRepository;
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

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public User changeCalorieLimit(final String username, final int newLimit) {
        User user = userRepository.findByUsername(username);
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
