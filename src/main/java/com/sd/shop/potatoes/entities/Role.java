package com.sd.shop.potatoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'MERCHANT', 'BUYER')", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new HashSet<>();

    public Role(Type roleType) {
        this.type = roleType;
    }

    public enum Type {
        ADMIN,
        MERCHANT,
        BUYER
    }
}
