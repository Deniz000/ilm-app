package com.ilim.app.dto.attendance;

import lombok.Data;

@Data
public class AttendanceResponse {
    private String status;
    private String lessonName;
    private String username;
    private String date;
}