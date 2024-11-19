package com.exchangerate.currencyexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Implement authentication for the exposed endpoints
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Set up the security configuration
     * @param httpSecurity httpSecurity
     * @return securityFilterChain which ensures that the
     * /api/** endpoints are accessible for authenticated users
     * @throws Exception exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/**")
                                .authenticated()
                                .anyRequest().permitAll()
                        ).httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    /**
     * Add a custom user
     * @param passwordEncoder password encoder mechanism to use
     *                        Using BCrypt Password Encoder to
     *                        securely hash passwords
     * @return user with specified username and encoded password
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("ashra")
                .password(passwordEncoder.encode("password"))
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Password Encoder
     * @return BCrypt Password Encoder securely hash passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
