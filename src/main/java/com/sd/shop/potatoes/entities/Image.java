package com.sd.shop.potatoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Image extends BaseEntity {
    @Column
    private String name;

    @Column(length = 1000000)
    private String base64;

    @Column
    @NotNull
    private Long productId;
}
