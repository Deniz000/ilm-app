package com.ilim.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "classroom_memberships")
@NoArgsConstructor
public class ClassroomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // student
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private UserEntity student;

    // Classroom
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classroom_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Classroom classroom;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();
}

