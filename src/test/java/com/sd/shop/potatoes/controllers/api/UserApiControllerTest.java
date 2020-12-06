package com.sd.shop.potatoes.controllers.api;

import com.sd.shop.potatoes.entities.User;
import com.sd.shop.potatoes.repositories.UserRepository;
import com.sd.shop.potatoes.utils.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private UserRepository userRepository;

    private String token;

    @BeforeEach
    void setUp() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User(
                "jonas",
                "Jonas",
                "Jonauskas",
                "password",
                "password",
                User.Role.ADMIN
        )));


        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        UserDetails user =
                new org.springframework.security.core.userdetails.User("jonas", "password", grantedAuthorities);

        token = jwtTokenUtil.generateToken(user);

    }

    @Test
    @WithMockUser(username = "jonas", roles = { "ADMIN" })
    void mustReturnAllUsers() throws Exception {
        ResultActions actions = mockMvc.perform(get("/api/users").header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        actions.andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].name").value("Jonas"));
    }
}