package com.ilim.app.business.impl;

import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dto.material.MaterialRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.business.services.MaterialService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.dataAccess.MaterialRepository;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.Material;
import com.ilim.app.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ilim.app.core.util.EntityUpdateUtil.updateIfNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final ModelMapperService modelMapper;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional
    public MaterialResponse addMaterial(MaterialRequest request) {
        log.info("Adding material {}", request.toString());
        if (validationHelper.validateByName(Material.class, request.getTitle()) ) {
            throw new EntityAlreadyExits("Material already exists" + request.getTitle());
        }
        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, request.getLessonId());
        UserEntity user = validationHelper.getIfExistsById(UserEntity.class, request.getUserId());
        Material material = modelMapper.forRequest().map(request, Material.class);
        material.setLesson(lesson);
        material.setUploadedBy(user);
        materialRepository.save(material);
        log.info("Material {} added", material.getTitle());
        return modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    @Override
    public MaterialResponse getMaterialById(Long id) {
        log.info("Getting material by id: {}", id);
        Material material = validationHelper.getIfExistsById(Material.class, id);
        return modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    @Override
    @Transactional
    public MaterialResponse updateMaterial(MaterialRequest request) {
        log.info("Updating material with ID: {}", request.getId());
        Material material = validationHelper.getIfExistsById(Material.class, request.getId());
        updateIfNotNull(material::setTitle, request.getTitle());
        updateIfNotNull(material::setFileUrl, request.getFileUrl());
        updateIfNotNull(material::setLesson,validationHelper.getIfExistsById(Lesson.class, request.getLessonId()));
        materialRepository.save(material);
        log.info("Material updated with ID: {}", request.getId());
        return modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    @Override
    @Transactional
    public void deleteMaterial(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Material material = validationHelper.getIfExistsById(Material.class, id);
        materialRepository.deleteById(material.getId());
        log.info("Notification with ID: {} deleted.", id);
    }

}
