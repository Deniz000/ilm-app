package com.ilim.app.dto.lesson;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonUpdateRequest {

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Size(max = 500, message = "Content can be up to 10000 characters")
    private String content;

    @NotNull(message = "Call time cannot be null")
    private LocalDateTime callTime = LocalDateTime.now();

    @NotNull(message = "Call link cannot be null")
    private String callLink;

    @NotNull(message = "Classroom ID cannot be null")
    private Long classroomId;
}
