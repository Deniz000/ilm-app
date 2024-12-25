package com.ilim.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "lessons")
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caller_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private UserEntity caller;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lessons_participants",
            joinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<UserEntity> participants = new HashSet<>();

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "call_time", nullable = false, updatable=false)
    private LocalDateTime callTime;

    @Column(name = "duration")
    private int duration;

    @Column(name = "call_link", nullable = false)
    private String callLink; // Sesli arama linki (örneğin, Zoom/Google Meet)

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private Set<Material> materials = new HashSet<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<CalendarEvent> calendarEvents;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Classroom classroom;
}

