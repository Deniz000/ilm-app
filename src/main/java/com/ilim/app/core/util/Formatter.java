package com.ilim.app.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Formatter {
    public String getFormattedCallTime(LocalDateTime callTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return callTime != null ? callTime.format(formatter) : null;
    }

}
