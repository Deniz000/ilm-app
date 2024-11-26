package com.ilim.app.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class CreateAttendanceRequest {
    private Long lessonId;
    private Long userId;
    private String status;
}
