package com.ilim.app.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomRequest {
    private Long createdBy;
    private String name;
    private String classCode;
    private LocalDateTime createdAt;
}
