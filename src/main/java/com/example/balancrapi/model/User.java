package com.example.balancrapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Minimal placeholder for the application's User entity.
 * <p>
 * Authentication, roles (user/admin) and the real set of fields are owned by the
 * Spring Security module, which is being built separately. This stub only exists
 * so that entities like {@link FixedEvent} have a concrete type to hold a
 * ManyToOne relation to, and so {@code @AuthenticationPrincipal User user} can be
 * used in controllers ahead of that work landing. Extend/replace this once the
 * security module is in place.
 * <p>
 * Table is named "app_user" rather than "user" because USER is a reserved
 * word in H2 (and several other SQL dialects).
 */
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
