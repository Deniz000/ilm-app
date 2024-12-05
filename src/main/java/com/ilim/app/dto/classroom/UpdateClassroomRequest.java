package com.ilim.app.dto.classroom;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateClassroomRequest {

    @NotNull(message = "Classroom id cannot be null")
    private Long id;
    @NotNull(message = "Classroom name cannot be null")
    private String name;
}
