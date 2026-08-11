package com.skala.quiz.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "learners", uniqueConstraints = @UniqueConstraint(name = "uk_learner_username", columnNames = "username"))
public class Learner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String username;
    @Column(nullable = false, unique = true, length = 36)
    private String accessToken;
    @Column(nullable = false, length = 60)
    private String passwordHash;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Learner() {}
    public Learner(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.accessToken = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
    }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getAccessToken() { return accessToken; }
    public String getPasswordHash() { return passwordHash; }
    public void refreshAccessToken() { this.accessToken = UUID.randomUUID().toString(); }
}
