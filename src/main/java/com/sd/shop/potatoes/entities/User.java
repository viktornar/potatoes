package com.sd.shop.potatoes.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ScriptAssert(
        lang = "javascript",
        script="_this.passwordConfirm === _this.password",
        message = "Password doesn't match",
        reportOn = "passwordConfirm"
)
public class User extends BaseEntity {
    @Column(unique = true)
    @NotBlank
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @NotBlank
    @Length(min=4, max = 10)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'MERCHANT', 'BUYER')", nullable = false)
    private Role role;

    @Transient
    @NotBlank
    @Length(min=4, max = 10)
    private String passwordConfirm;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<Cart> carts = new HashSet<>();

    public User(String username, String name, String surname, String password, String passwordConfirm, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.role = role;
    }

    public enum Role {
        ADMIN("Admin"),
        MERCHANT("Merchant"),
        BUYER("Buyer");

        private final String displayValue;

        private Role(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }
}
