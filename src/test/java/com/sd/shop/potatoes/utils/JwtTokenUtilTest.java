package com.sd.shop.potatoes.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenUtilTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    String token;
    UserDetails user;

    @BeforeEach
    void setUp() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        user =
                new org.springframework.security.core.userdetails.User("jonas", "password", grantedAuthorities);

        token = jwtTokenUtil.generateToken(user);
    }

    @Test
    void getUsernameFromToken() {
        assertEquals("jonas", jwtTokenUtil.getUsernameFromToken(token));
    }

    @Test
    void validateToken() {
        assertTrue(jwtTokenUtil.validateToken(token, user));
    }
}