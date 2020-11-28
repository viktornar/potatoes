package com.sd.shop.potatoes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

// Dao authentication example.
//    private final PasswordEncoder passwordEncoder;
//    private final UserPrincipalDetailsService userPrincipalDetailsService;
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
//        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
//        return daoAuthenticationProvider;
//    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
// Example of integration database.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
// Example with in memory user.
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("BUYER")
//        .and()
//                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("BUYER")
//        .and()
//                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/register", "/js/**", "/images/**", "/css/**", "/api/authenticate").permitAll()
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
