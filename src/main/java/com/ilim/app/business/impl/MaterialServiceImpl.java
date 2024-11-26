package com.ilim.app.business.impl;

import com.ilim.app.business.validationhelper.MaterialValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.business.services.MaterialService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.exceptions.MaterialNotFoundException;
import com.ilim.app.dataAccess.MaterialRepository;
import com.ilim.app.entities.Material;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialValidationHelper validationHelper;
    private final ModelMapperService modelMapper;

    @Override
    @Transactional
    public MaterialResponse addMaterial(MaterialRequest request) {
        log.info("Adding material {}", request.toString());
        if (validationHelper.validateMaterialExists(request.getTitle())) {
            throw new EntityAlreadyExits("Material already exists" + request.getTitle());
        }
        Material material = modelMapper.forRequest().map(request, Material.class);
        materialRepository.save(material);
        log.info("Material {} added", material.getTitle());
        return modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    @Override
    public MaterialResponse getMaterialById(Long id) {
        log.info("Getting material by id: {}", id);
        Material material = validationHelper.getMaterialIfExists(id);
        return modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    @Override
    @Transactional
    public MaterialResponse updateMaterial(Long id, MaterialRequest response) {
        log.info("Updating material with ID: {}", id);
        Material material = validationHelper.getMaterialIfExists(id);
        material.setTitle(response.getTitle());
        material.setFileUrl(response.getFileUrl());
        material.setLesson(validationHelper.getLessonIfExist(response.getLessonId()));
        materialRepository.save(material);
        log.info("Material updated with ID: {}", id);
        return modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    @Override
    @Transactional
    public void deleteMaterial(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Material material = validationHelper.getMaterialIfExists(id);
        materialRepository.deleteById(material.getId());
        log.info("Notification with ID: {} deleted.", id);
    }

    @Override
    public List<MaterialResponse> getMaterialsByLessonId(Long lessonId) {
        log.info("Fetching materials for Lesson with ID: {}", lessonId);
        return materialRepository.findByLessonId(lessonId)
                .stream()
                .map(material -> modelMapper.forResponse().map(material, MaterialResponse.class))
                .collect(Collectors.toList());
    }

}
