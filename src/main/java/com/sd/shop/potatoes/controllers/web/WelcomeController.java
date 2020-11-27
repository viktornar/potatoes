package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.entities.Cart;
import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @GetMapping(value = {"", "/", "/welcome"})
    String getWelcome(Model model) {
        int quantity = 0;

        if (cartRepository.existsByUserIdAndPurchasedFalse(1L)) {
            Cart cart = cartRepository.findByUserIdAndPurchasedFalse(1L);
            quantity = cart.getProducts().size();
        }

        model.addAttribute("quantity", quantity);
        model.addAttribute("products", productRepository.findAll());
        return "welcome/index";
    }
}
