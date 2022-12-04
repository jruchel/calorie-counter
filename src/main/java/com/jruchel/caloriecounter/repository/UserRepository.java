package com.jruchel.caloriecounter.repository;

import com.jruchel.caloriecounter.model.internal.User;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    default boolean isUsernameUnique(String username) {
        return findByUsername(username).orElse(null) == null;
    }

    default Optional<User> findByUsername(String username) {
        ExampleMatcher matcher =
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withMatcher(
                                "username",
                                ExampleMatcher.GenericPropertyMatcher.of(
                                        ExampleMatcher.StringMatcher.EXACT));
        Example<User> usernameExample =
                Example.of(User.builder().username(username).build(), matcher);
        return findAll(usernameExample).stream().findFirst();
    }
}
