package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.User;
import com.sd.shop.potatoes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserRepository userRepository;

    @GetMapping("/api/users")
    List<User> getUser() {
        return (List<User>) userRepository.findAll();
    }
}
