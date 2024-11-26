package com.ilim.app.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinClassroomRequest {
    private Long studentId;
    private String classCode;
}
