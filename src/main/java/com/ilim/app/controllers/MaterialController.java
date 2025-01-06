package com.ilim.app.controllers;

import com.ilim.app.business.services.MaterialService;
import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.entities.Material;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    //tested - 05
    @PostMapping("/upload")
    public ResponseEntity<String> uploadMaterial(
            @RequestPart("fileName") String fileName,
            @RequestPart("file") MultipartFile file,
            @RequestPart("lessonId") String lessonId,
            @RequestPart("uploadedBy") String uploadedBy){
        try {
            materialService.addMaterial(fileName,
                    file,
                    lessonId,
                    uploadedBy);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed.");
        }

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Material pdfFile = materialService.getFile(id);
        if (pdfFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pdfFile.getFileName() + "\"")
                .body(pdfFile.getData());
    }

    //tested - 05
    @PutMapping
    public ResponseEntity<MaterialResponse> updateMaterial(@Valid @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(materialService.updateMaterial(request));
    }

    //tested -05
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@Valid @PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
