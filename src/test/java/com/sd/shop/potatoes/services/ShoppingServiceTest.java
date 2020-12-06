package com.sd.shop.potatoes.services;

import com.sd.shop.potatoes.entities.Cart;
import com.sd.shop.potatoes.entities.Product;
import com.sd.shop.potatoes.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingServiceTest {
    @InjectMocks
    public ShoppingService shoppingService;

    @Mock
    public CartRepository cartRepository;

    // Stub
    private final Cart cart = new Cart();
    private final Product product = new Product();


    @BeforeEach
    public void setUp() {
        product.setId(1L);
        cart.setProducts(new LinkedList<>(Arrays.asList(product)));
        when(cartRepository.findByUserIdAndPurchasedFalse(anyLong())).thenReturn(cart);
        when(cartRepository.existsByUserIdAndPurchasedFalse(anyLong())).thenReturn(true);
    }

    @Test
    @Description("Must get quantity equal to 1 for given user id.")
    public void mustGetOneQuantityForGivenUser() {
        int quantity = shoppingService.getProductQuantityForUserId(1L);
        assertEquals(1, quantity);
    }

    @Description("Must get quantity equal to 1 for given user ids.")
    @ParameterizedTest
    @ValueSource(longs = { 1L, 2L })
    public void mustGetOneQuantityForGivenUsers(Long id) {
        int quantity = shoppingService.getProductQuantityForUserId(id);
        assertEquals(1, quantity);
    }

    @Test
    void addOrRemoveProductToUserCart() {
        shoppingService.addOrRemoveProductToUserCart(1L, false, product);
        int quantity = shoppingService.getProductQuantityForUserId(1L);
        assertEquals(0, quantity);
    }
}
