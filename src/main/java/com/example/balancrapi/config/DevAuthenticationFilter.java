package com.example.balancrapi.config;

import com.example.balancrapi.model.User;
import com.example.balancrapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * TEMPORARY, dev-only. There is no real login flow yet, so this filter treats
 * every incoming request as if it were made by the fixed dev user seeded by
 * {@link DevUserSeeder}, so that {@code @AuthenticationPrincipal User} in
 * controllers has something real to bind to during local/manual testing
 * (Postman, curl, etc). Delete this class - and the permitAll rule in
 * {@link SecurityConfig} - once real authentication is implemented.
 */
@Component
public class DevAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public DevAuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        userRepository.findByEmail(DevUserSeeder.DEV_USER_EMAIL).ifPresent(this::authenticateAs);
        filterChain.doFilter(request, response);
    }

    private void authenticateAs(User user) {
        var authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
