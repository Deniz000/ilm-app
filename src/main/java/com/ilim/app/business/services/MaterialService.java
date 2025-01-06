package com.ilim.app.business.services;

import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.entities.Material;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MaterialService {
    void addMaterial(
            String fileName,
            MultipartFile file,
            String lessonId,
            String uploadedBy
    ) throws IOException;
    MaterialResponse getMaterialById(Long id);
    MaterialResponse updateMaterial(MaterialRequest response);
    void deleteMaterial(Long id);
    Material getFile(Long id);
}
