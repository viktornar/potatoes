package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.Image;
import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.exceptions.ImageConflict;
import com.sd.shop.potatoes.exceptions.ProductNotFound;
import com.sd.shop.potatoes.repositories.ImageRepository;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageApiController {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @GetMapping("/api/images")
    public List<Image> getImages() {
        return (List<Image>) Optional.of(imageRepository.findAll()).orElse(new ArrayList<>());
    }

    @PostMapping("/api/images/new")
    public Image createNewImage(@RequestBody Image image) throws ProductNotFound, ImageConflict {
        if (productRepository.existsById(image.getProductId())) {
            return Optional.of(imageRepository.save(image)).orElseThrow(() -> new ImageConflict("Was not able to save image"));
        }

        throw new ProductNotFound(String.format("Product with id %s not exist", image.getProductId()));
    }
}
