package com.ilim.app.business.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private Long userId; // TODO redundant
    private Long lessonId; // String al
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}