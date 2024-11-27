package com.ilim.app.controllers;

import com.ilim.app.business.services.MaterialService;
import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity<MaterialResponse> addMaterial(@Valid @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(materialService.addMaterial(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getMaterialById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> updateMaterial(@Valid @PathVariable Long id,@Valid @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(materialService.updateMaterial(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@Valid @PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<MaterialResponse>> getMaterialsByLessonId(@Valid @PathVariable Long lessonId) {
        return ResponseEntity.ok(materialService.getMaterialsByLessonId(lessonId));
    }
}
