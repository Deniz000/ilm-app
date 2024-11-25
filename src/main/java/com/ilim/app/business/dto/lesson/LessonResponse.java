package com.ilim.app.business.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponse {
    private String categoryName;
    private String callerUsername;
    private String title;
    private String content;
    private LocalDateTime callTime;
    private int duration;
    private String callLink;
    private String classroomName;

}
