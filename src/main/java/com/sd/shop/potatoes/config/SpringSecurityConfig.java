package com.sd.shop.potatoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("BUYER")
        .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("BUYER")
        .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/js/**", "/images/**", "/css/**", "/api/authenticate").permitAll()
                .antMatchers("/admin/**").hasAnyRole("MERCHANT", "ADMIN")
                .antMatchers("/products/**").hasRole("BUYER")
                .anyRequest().authenticated()
        .and()
                .formLogin().loginPage("/login").permitAll()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/", true)
                .usernameParameter("username")
                .passwordParameter("password")
        .and()
                .logout().permitAll()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout")
                )
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
        .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
