package com.ilim.app.dto.classroom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentsResponse {
    private Long id;
    private String username;
    private String email;

}
