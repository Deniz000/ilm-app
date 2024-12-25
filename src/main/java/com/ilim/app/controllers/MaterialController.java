package com.ilim.app.controllers;

import com.ilim.app.business.services.MaterialService;
import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    //tested - 05
    @PostMapping
    public ResponseEntity<MaterialResponse> addMaterial(@Valid @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(materialService.addMaterial(request));
    }

    //tested - 05
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
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
