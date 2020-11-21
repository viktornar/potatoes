package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final ProductRepository productRepository;

    @GetMapping(value = {"", "/", "/welcome"})
    String getWelcome(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "welcome/index";
    }
}
