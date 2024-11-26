package com.ilim.app.dto.classroom;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomResponse {
    private String createdBy; //TODO silebilirsin
    private String name;
    private String classCode;
    private LocalDateTime createdAt;
}