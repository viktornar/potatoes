package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.User;
import com.sd.shop.potatoes.services.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final ShoppingService shoppingService;

    @GetMapping("/api/users")
    List<User> getUser() {
        return shoppingService.findAllUsers();
    }
}
