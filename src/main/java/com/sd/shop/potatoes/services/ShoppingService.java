package com.sd.shop.potatoes.services;

import com.sd.shop.potatoes.entities.Cart;
import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.CartRepository;
import com.sd.shop.potatoes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final CartRepository cartRepository;

    public int getProductQuantityForUserId(Long id) {
        int quantity = 0;

        if (cartRepository.existsByUserIdAndPurchasedFalse(id)) {
            Cart cart = cartRepository.findByUserIdAndPurchasedFalse(id);
            List<Product> products = cart.getProducts();
            quantity = products.size();
        }

        return quantity;
    }

    public void addOrRemoveProductToUserCart(Long id, boolean increase, Product p) {
        Cart cart = new Cart();

        if (cartRepository.existsByUserIdAndPurchasedFalse(id)) {
            cart = cartRepository.findByUserIdAndPurchasedFalse(id);
            if (increase) {
                cart.getProducts().add(p);
            } else {
                List<Product> cartProducts = cart.getProducts();
                for (Product product : cartProducts) {
                    if (product.getId().equals(p.getId())) {
                        cartProducts.remove(product);
                        break;
                    }
                }
            }
        } else {
            cart.setProducts(new LinkedList<>(Arrays.asList(p)));
            cart.setUserId(id);
        }

        cartRepository.save(cart);
    }
}
