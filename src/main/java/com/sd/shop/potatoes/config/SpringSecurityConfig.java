package com.sd.shop.potatoes.config;

import com.sd.shop.potatoes.components.JwtAuthenticationEntryPoint;
import com.sd.shop.potatoes.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public SpringSecurityConfig(
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
            JwtRequestFilter jwtRequestFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

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
    protected void configure(final HttpSecurity http) throws Exception {
// This will work for JWT token based authentication
        http.authorizeRequests()
                .antMatchers("/", "/js/**", "/register", "/css/**", "/images/**", "/api/authenticate").permitAll()
                .antMatchers("/api/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                // Make sure we use stateless session; session won't be used to
                // store user's state
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

// This will work with session based app
//        http.authorizeRequests()
//                .antMatchers("/", "/register", "/js/**", "/images/**", "/css/**", "/api/authenticate").permitAll()
//                .antMatchers("/admin/**").hasAnyRole("MERCHANT", "ADMIN")
//                .antMatchers("/products/**").hasRole("BUYER")
//                .anyRequest().authenticated()
//        .and()
//                .formLogin().loginPage("/login").permitAll()
//                .failureUrl("/login?error")
//                .defaultSuccessUrl("/", true)
//                .usernameParameter("username")
//                .passwordParameter("password")
//        .and()
//                .logout().permitAll()
//                .logoutRequestMatcher(
//                        new AntPathRequestMatcher("/logout")
//                )
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//        .and()
//                .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
//                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
