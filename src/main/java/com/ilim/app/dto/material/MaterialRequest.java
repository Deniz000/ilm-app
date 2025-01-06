package com.ilim.app.dto.material;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequest {

    private Long id;

    @NotNull(message = "Material name shouldn't be empty")
    @Size(min = 3, message = "enter at least 3 letters")
    private String fileName;

    private MultipartFile file;

    @NotNull(message = "Please, choose the lesson first")
    private String lessonId;

    @NotNull(message = "Who is uploading this doc? ")
    private String userId;


}