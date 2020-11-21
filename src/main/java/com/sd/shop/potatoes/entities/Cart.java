package com.sd.shop.potatoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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
    private Set<Product> products = new HashSet<>();
}
