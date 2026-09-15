package com.example.balancrapi.config;

import com.example.balancrapi.model.User;
import com.example.balancrapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TEMPORARY, dev-only. Ensures a fixed test user exists in the H2 database on
 * startup so there is a real row for FixedEvent's user_id foreign key to point
 * at while there is no real registration/login flow yet. {@link DevAuthenticationFilter}
 * looks this user up by email and treats every request as if it came from
 * them. Delete this class once real user registration exists.
 */
@Component
public class DevUserSeeder implements CommandLineRunner {

    public static final String DEV_USER_EMAIL = "dev@balancr.local";

    private final UserRepository userRepository;

    public DevUserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.findByEmail(DEV_USER_EMAIL).orElseGet(() -> {
            User user = new User();
            user.setEmail(DEV_USER_EMAIL);
            return userRepository.save(user);
        });
    }
}
