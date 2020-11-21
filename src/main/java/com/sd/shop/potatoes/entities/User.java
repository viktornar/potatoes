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
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @NotBlank
    @Length(min=4, max = 10)
    private String password;

    @Transient
    @NotBlank
    @Length(min=4, max = 10)
    private String passwordConfirm;

    @ManyToOne
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<Cart> cart = new HashSet<>();

    public User(String username, String name, String surname, String password, String passwordConfirm, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.role = role;
    }
}
