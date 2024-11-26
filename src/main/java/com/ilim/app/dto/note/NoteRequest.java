package com.ilim.app.dto.note;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    @NotNull(message = "Content cannot be null")
    @Size(min = 1, max = 2000, message = "Content must be between 1 and 1000 characters")
    private String content;
}