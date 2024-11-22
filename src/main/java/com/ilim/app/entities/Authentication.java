package com.ilim.app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "authentication")
@NoArgsConstructor
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user; // User sınıfı ile ilişki (Many-to-One)

    @Column(name = "token", nullable = false, unique = true)
    private String token; // Token değeri

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Token oluşturulma tarihi

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt; // Token geçerlilik süresi

}
