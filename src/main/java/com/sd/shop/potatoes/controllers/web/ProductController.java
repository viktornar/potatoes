package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.repositories.ProductRepository;
import com.sd.shop.potatoes.services.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ShoppingService shoppingService;

    @GetMapping("/products")
    String getAllProducts(Model model) {
        int quantity = shoppingService.getProductQuantityForUserId(1L);

        model.addAttribute("quantity", quantity);
        model.addAttribute("products", Optional.of(productRepository.findAll()).orElse(new ArrayList<>()));

        return "product/index";
    }

    @PostMapping("/products/{productId}/addToCart")
    String addProductToCart(@PathVariable Long productId) {
        updateCartWithProduct(productId, true);

        return "redirect:/products";
    }

    @PostMapping("/products/{productId}/increase")
    String increaseProduct(@PathVariable Long productId) {
        updateCartWithProduct(productId, true);

        return "redirect:/carts";
    }

    @PostMapping("/products/{productId}/decrease")
    String decreaseProduct(@PathVariable Long productId) {
        updateCartWithProduct(productId, false);

        return "redirect:/carts";
    }

    private void updateCartWithProduct(Long productId, boolean increase) {
        productRepository.findById(productId).ifPresent(p -> {
            shoppingService.addOrRemoveProductToUserCart(1L, increase, p);
        });
    }
}
