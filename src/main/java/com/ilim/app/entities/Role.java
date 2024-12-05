package com.ilim.app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private RoleName name;

    public enum RoleName {
        ADMIN,
        TEACHER,
        STUDENT;
        public static RoleName fromString(String name) {
            return Arrays.stream(values())
                    .filter(n-> n.name().equalsIgnoreCase(name))
                    .findFirst()
                    .orElseThrow(()-> new IllegalArgumentException("Invalid role name: " + name));
        }
    }
}

