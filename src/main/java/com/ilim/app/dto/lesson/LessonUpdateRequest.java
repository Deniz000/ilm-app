package com.ilim.app.dto.lesson;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonUpdateRequest {
    //notnull olmayanlar boş bırakılabilir, yani onlar değiştirmeyebilir

    @NotNull(message = "id can not be null. this will come from app as a default")
    private Long id;
    @NotNull(message = "caller id can not be null. this will come from app as a default")
    private Long callerId;
    private Long categoryId;
    private String title;
    private String content;
    private LocalDateTime callTime = LocalDateTime.now();
    private String callLink;
}
