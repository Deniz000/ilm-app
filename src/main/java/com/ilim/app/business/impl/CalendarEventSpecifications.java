package com.ilim.app.business.impl;
import com.ilim.app.entities.CalendarEvent;
import org.springframework.data.jpa.domain.Specification;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalendarEventSpecifications {

    public static Specification<CalendarEvent> filterByDay(String day) {
        return (root, query, criteriaBuilder) -> {
            if (day == null || day.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
            LocalDateTime endOfToday = now.toLocalDate().atTime(LocalTime.MAX);

            if ("today".equalsIgnoreCase(day)) {
                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startOfToday),
                        criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), endOfToday)
                );
            }
            return criteriaBuilder.conjunction(); // Geçersiz bir day verilmişse hiçbir filtre eklenmez
        };
    }

    public static Specification<CalendarEvent> filterByWeek(String week) {
        return (root, query, criteriaBuilder) -> {
            if (week == null || week.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
            LocalDateTime endOfWeek = startOfWeek.plusDays(6).toLocalDate().atTime(LocalTime.MAX);

            if ("current".equalsIgnoreCase(week)) {
                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startOfWeek),
                        criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), endOfWeek)
                );
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<CalendarEvent> filterUpcoming(Boolean upcoming) {
        return (root, query, criteriaBuilder) -> {
            if (upcoming == null || !upcoming) {
                return criteriaBuilder.conjunction();
            }

            LocalDateTime now = LocalDateTime.now();
            return criteriaBuilder.greaterThan(root.get("startTime"), now);
        };
    }
}
