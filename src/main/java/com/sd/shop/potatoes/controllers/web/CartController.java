package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.services.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final ShoppingService shoppingService;

    @GetMapping("carts")
    String getCurrentUserCart(Model model) {
        List<Product> products = new ArrayList<>();
        int quantity = shoppingService.getProductQuantityForUserId(1L);

        model.addAttribute("quantity", quantity);
        model.addAttribute("items", groupProducts(products));

        return "cart/index";
    }

    public Map<Product, Long> groupProducts(List<Product> products) {
        return products.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
