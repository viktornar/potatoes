package com.sd.shop.potatoes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
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
                .defaultSuccessUrl("/products", true)
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
}
