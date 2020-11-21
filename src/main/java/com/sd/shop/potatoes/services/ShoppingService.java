package com.sd.shop.potatoes.services;

import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.entities.User;
import com.sd.shop.potatoes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;

    public Iterable<Product> findAllProducts() {
        return null;
    }

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User saveUser() {
        return null;
    }

    public Optional<User> findUserById(Optional<Integer> id) {
        if (id.isPresent()) {
            if (id.get() == 2) {
                return Optional.empty();
            }

            User user = new User();
            user.setUsername("Testas");
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
