package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.repositories.ProductRepository;
import com.sd.shop.potatoes.services.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final ProductRepository productRepository;
    private final ShoppingService shoppingService;

    @GetMapping(value = {"", "/", "/welcome"})
    String getWelcome(Model model) {
        int quantity = shoppingService.getProductQuantityForUserId(1L);

        model.addAttribute("quantity", quantity);
        model.addAttribute("products", productRepository.findAll());

        return "welcome/index";
    }
}
