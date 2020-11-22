package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final ProductRepository productRepository;

    @GetMapping("/admin")
    String getProductsForEditing(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/index";
    }

    @PostMapping("/admin/delete/{id}")
    String deleteProductById(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    String getProductByIdForEdit(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(new Product());
        model.addAttribute(product);
        return "admin/edit";
    }

    @PostMapping("/admin/edit/{id}/save")
    String saveProductByIdAfterEdit(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/edit";
        }

        productRepository.save(product);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/add")
    String addNewProduct(Model model) {
        model.addAttribute(new Product());
        return "admin/add";
    }

    @PostMapping("/admin/add")
    String getProductByIdForAdd(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/add";
        }

        productRepository.save(product);

        return "redirect:/admin";
    }
}
