package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductRepository productRepository;

    @GetMapping("/api/products")
    List<Product> getProducts() {
        return (List<Product>) Optional.of(productRepository.findAll()).orElse(new ArrayList<>());
    }
}
