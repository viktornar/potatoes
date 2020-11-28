package com.sd.shop.potatoes.services;

import com.sd.shop.potatoes.entities.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    List<User> findAll();
}
