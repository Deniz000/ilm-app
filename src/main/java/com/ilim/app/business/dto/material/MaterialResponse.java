package com.ilim.app.business.dto.material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialResponse {
    private String lessonTitle;
    private String title;
    private String fileUrl;
}
