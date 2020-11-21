package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.services.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final ShoppingService shoppingService;

    @GetMapping("/users")
    String getUser(Model model) {
        model.addAttribute(shoppingService.findAllUsers());
        return "user/list";
    }


    @GetMapping("/users/{id}")
    String getUser(
            @PathVariable Optional<Integer> id,
            Model model,
            @RequestParam(required = false) Optional<String> name
    ) {
        if (shoppingService.findUserById(id).isEmpty()) {
            return "redirect:/users";
        }

        model.addAttribute(shoppingService.findUserById(id).get());
        return "user/index";
    }
}
