package com.ilim.app.business.dto.material;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequest {

    @NotNull(message = "Please, choose the lesson first")
    private Long lessonId;

    @NotNull(message = "Material title shouldn't be empty")
    @Size(min = 3, message = "enter at least 3 letters")
    private String title;

    @NotNull(message = "file_url")
    private String fileUrl;

}
