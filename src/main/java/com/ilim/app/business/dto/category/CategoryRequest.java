package com.ilim.app.business.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotNull(message = "name can not be empty")
    private String name;

    @NotNull(message = "name can not be empty")
    @Size(min = 3, max = 250, message = "at least, you should write 3 letters")
    private String description;

}
