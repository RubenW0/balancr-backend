package com.example.balancrapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * TEMPORARY placeholder. Adding spring-boot-starter-security (needed for
 * {@code @AuthenticationPrincipal}) makes Spring Security lock every endpoint
 * behind basic auth by default, which would break the app before the real
 * auth/role module exists. This permits all requests for now so
 * FixedEventController is reachable, and installs {@link DevAuthenticationFilter}
 * so there is a real authenticated principal to test with in the meantime.
 * Replace all of this with real authentication and user/admin role rules
 * when the security module is built.
 */
@Configuration
public class SecurityConfig {

    private final DevAuthenticationFilter devAuthenticationFilter;

    public SecurityConfig(DevAuthenticationFilter devAuthenticationFilter) {
        this.devAuthenticationFilter = devAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(devAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
