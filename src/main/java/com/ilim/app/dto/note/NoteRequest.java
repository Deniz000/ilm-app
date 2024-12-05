package com.ilim.app.dto.note;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {

    private Long id;
    @NotNull(message = "Content cannot be null")
    @Size(min = 1, max = 2000, message = "Content must be between 1 and 1000 characters")
    private String title;

    @NotNull(message = "Content cannot be null")
    private String content;

    @NotNull(message = "User will come default but you give it anyway")
    private Long createdBy;

    @NotNull(message = "lesson will come default but you give it anyway")
    private Long lessonId;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();


}