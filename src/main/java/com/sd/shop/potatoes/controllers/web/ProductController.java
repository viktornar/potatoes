package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.entities.Cart;
import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @RequestMapping("/products")
    String getAllProducts(Model model) {
        int quantity = 0;

        if (cartRepository.existsByUserIdAndPurchasedFalse(1L)) {
            Cart cart = cartRepository.findByUserIdAndPurchasedFalse(1L);
            quantity = cart.getProducts().size();
        }

        model.addAttribute("quantity", quantity);
        model.addAttribute("products", Optional.of(productRepository.findAll()).orElse(new ArrayList<>()));

        return "product/index";
    }

    @RequestMapping("/products/{productId}/addToCart")
    String getAllProducts(@PathVariable Long productId) {
        updateCartWithProduct(productId, true);

        return "redirect:/products";
    }

    @RequestMapping("/products/{productId}/increase")
    String increaseProduct(@PathVariable Long productId) {
        updateCartWithProduct(productId, true);

        return "redirect:/carts";
    }

    @RequestMapping("/products/{productId}/decrease")
    String decreaseProduct(@PathVariable Long productId) {
        updateCartWithProduct(productId, false);

        return "redirect:/carts";
    }

    private void updateCartWithProduct(Long productId, boolean increase) {
        productRepository.findById(productId).ifPresent(p -> {
            Cart cart = new Cart();

            if (cartRepository.existsByUserIdAndPurchasedFalse(1L)) {
                cart = cartRepository.findByUserIdAndPurchasedFalse(1L);
                if (increase) {
                    cart.getProducts().add(p);
                } else {
                    cart.getProducts().remove(p);
                }
            } else {
                cart.setProducts(Collections.singletonList(p));
                cart.setUserId(1L);
            }

            cartRepository.save(cart);
        });
    }
}
