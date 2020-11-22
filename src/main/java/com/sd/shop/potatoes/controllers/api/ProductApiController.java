package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/api/products/new")
    Product createNewProduct(@RequestBody Product product) {
        log.info("New product from body: {}", product);
        return Optional.of(productRepository.save(product)).orElse(new Product());
    }
}
