package com.ilim.app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "calendar_events")
@NoArgsConstructor
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    private UserEntity creator;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserEntity> participants;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    public enum EventType {
        LESSON,
        MEETING,
        OTHER;

        public static EventType fromString(String eventType) {
            return Arrays.stream(values())
                    .filter(type -> type.name().equalsIgnoreCase(eventType))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type: " + eventType));
        }
    }
}
