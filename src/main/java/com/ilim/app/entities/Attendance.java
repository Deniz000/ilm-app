package com.ilim.app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@Entity
@Table(name = "attendances")
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status; // Enum tipi

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", nullable = false)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private CalendarEvent event;

    @Column(name = "attendance_date", nullable = false)
    private LocalDateTime attendanceDate;

    public enum AttendanceStatus {
        ATTENDED,
        ABSENT,
        EXCUSED,
        WILL_ATTEND,
        NOT_WILL_ATTEND,
        LATE;

        public static AttendanceStatus fromString(String status) {
            return Arrays.stream(values())
                    .filter(s -> s.name().equalsIgnoreCase(status))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + status));
        }
    }

}
