package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.entities.Cart;
import com.sd.shop.potatoes.entities.User;
import com.sd.shop.potatoes.exceptions.UserNotFound;
import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @GetMapping("/users")
    public String getUser(Model model, RedirectAttributes redirectAttributes) {
        int quantity = 0;

        if (cartRepository.existsByUserIdAndPurchasedFalse(1L)) {
            Cart cart = cartRepository.findByUserIdAndPurchasedFalse(1L);
            quantity = cart.getProducts().size();
        }

        model.addAttribute("quantity", quantity);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userError", redirectAttributes.getAttribute("userError"));

        return "user/index";
    }


    @GetMapping("/users/{id}/edit")
    public String editUser(
            @PathVariable Long id,
            Model model
    ) {
        int quantity = 0;

        if (cartRepository.existsByUserIdAndPurchasedFalse(1L)) {
            Cart cart = cartRepository.findByUserIdAndPurchasedFalse(1L);
            quantity = cart.getProducts().size();
        }

        model.addAttribute("quantity", quantity);
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return "redirect:/users";
        }

        User user = userOptional.get();

        user.setPasswordConfirm(user.getPassword());

        model.addAttribute("user", user);

        return "user/edit";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(
            @PathVariable Long id,
            @RequestParam(required = false) Optional<String> name
    ) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.findById(id).get());
        }

        return "redirect:/users";
    }

    @PostMapping("/users/{id}/edit")
    public String saveEditedUser(
            @Valid @ModelAttribute User user,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "user/edit";
        }

        userRepository.findById(id).ifPresentOrElse(u -> {
            u.setName(user.getName());
            u.setSurname(user.getSurname());
            u.setUsername(user.getUsername());
            u.setRole(user.getRole());
            u.setPassword(user.getPassword());
            u.setPasswordConfirm(user.getPasswordConfirm());

            userRepository.save(u);
            redirectAttributes.addAttribute("userError", false);
        }, () -> {
            redirectAttributes.addAttribute("userError", true);
        });


        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(
            @PathVariable Long id
    ) {
        userRepository.deleteById(id);

        return "redirect:/users";
    }
}
