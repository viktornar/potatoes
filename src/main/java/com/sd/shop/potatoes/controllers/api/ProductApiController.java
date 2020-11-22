package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/api/products/{id}")
    Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElse(new Product());
    }

    @GetMapping("/api/products/queryByNameAndPrice")
    List<Product> getProductsByQuery(@RequestParam String name, @RequestParam Double price) {
        return (List<Product>) Optional.of(productRepository.findByNameAndPriceGreaterThan(name, price)).orElse(new ArrayList<>());
    }

    @GetMapping("/api/products/queryByNameOrPriceOrCount")
    List<Product> getProductsByQuery(@RequestParam String name, @RequestParam Double price, @RequestParam int count) {
        return (List<Product>) Optional.of(productRepository.findByNameOrPriceGreaterThanOrCountGreaterThan(name, price, count)).orElse(new ArrayList<>());
    }
}
