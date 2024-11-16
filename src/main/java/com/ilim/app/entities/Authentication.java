package com.ilim.app.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authentication")
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "expires_at")
    private String expiresAt;

    // Constructor
    public Authentication(Long userId, String token, String createdAt, String expiresAt) {
        this.userId = userId;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public Authentication() {

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    // toString Method
    @Override
    public String toString() {
        return "Authentication{" +
                "id=" + id +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                '}';
    }

    // equals Method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authentication that = (Authentication) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(token, that.token) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(expiresAt, that.expiresAt);
    }

    // hashCode Method
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, token, createdAt, expiresAt);
    }
}
