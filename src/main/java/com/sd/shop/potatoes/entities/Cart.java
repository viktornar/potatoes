package com.sd.shop.potatoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Cart extends BaseEntity {
    @Column
    private boolean purchased;

    @Column
    private String address;

    @Column
    @NotNull
    private Long userId;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name="cart_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<Product> products = new ArrayList<>();
}
