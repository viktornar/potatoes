package com.sd.shop.potatoes.integration.repositories;

import com.sd.shop.potatoes.entities.User;
import com.sd.shop.potatoes.exceptions.UserNotFound;
import com.sd.shop.potatoes.services.UserService;
import com.sd.shop.potatoes.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final User userStub = new User(
            "Jonas",
            "Jonas",
            "Jonauskas",
            "password",
            "password",
            User.Role.BUYER
    );

    @BeforeEach
    void setUp() {
        userService.save(userStub);
    }

    @Test
    @Description("Must find user by id since it is created in userService before test.")
    void mustFindUserById() throws UserNotFound {
        Optional<User> user = userRepository.findById(1L);

        if (user.isPresent()) {
            assertEquals(userStub.getName(), user.get().getName());
        } else {
            throw new UserNotFound("User not found.");
        }

    }

    @Test
    @Description("If we don't find user we will throw user not found exception with `User not found.` message.")
    void mustThrowUserNotFoundException() {
        Optional<User> user = userRepository.findById(2L);

        try {
            if (user.isPresent()) {
                assertEquals(userStub.getName(), user.get().getName());
            } else {
                throw new UserNotFound("User not found.");
            }
        } catch (UserNotFound e) {
            assertThat(e.getMessage()).startsWith("User not");
        }
    }
}