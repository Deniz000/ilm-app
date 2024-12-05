package com.ilim.app.dto.attendance;

import lombok.Data;

@Data
public class CreateAttendanceRequest {
    private Long userId;
    private String status;
    private Long eventId;
}
