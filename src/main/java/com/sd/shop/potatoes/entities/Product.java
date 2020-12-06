package com.sd.shop.potatoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Product extends BaseEntity {
    @Column
    private String name;
    @Column(length = 500)
    private String description;
    @Column
    @Positive
    private int count;
    @Column
    @Positive
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('EUR', 'US_DOLLAR')", nullable = false)
    private Currency currency;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<Cart> carts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productId")
    private Set<Image> images = new HashSet<>();

    public Product(String name, String description, int count, double price, Currency currency) {
        this.name = name;
        this.description = description;
        this.count = count;
        this.price = price;
        this.currency = currency;
    }

    public enum Currency {
        EUR ("€"),
        US_DOLLAR ("$");

        private final String displayValue;

        private Currency(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }
}
