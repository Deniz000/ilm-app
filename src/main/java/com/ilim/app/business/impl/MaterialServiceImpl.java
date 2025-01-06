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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public void addMaterial(
            String fileName,
            MultipartFile file,
            String lessonId,
            String uploadedBy
    ) throws IOException {
        log.info("Adding material {}", fileName);
        if (validationHelper.validateByName(Material.class, fileName) ) {
            throw new EntityAlreadyExits("Material already exists" + fileName);
        }
        Lesson lesson = validationHelper.getLessonsValidator().getIfExistsById(id(lessonId));
        UserEntity user = validationHelper.getUserValidator().getIfExistsById(id(uploadedBy));

        Material material = new Material();
        material.setFileName(fileName);
        material.setData(file.getBytes());
        material.setLesson(lesson);
        material.setUploadedBy(user);
        materialRepository.save(material);
        log.info("Material {} added", material.getFileName());
        modelMapper.forResponse().map(material, MaterialResponse.class);
    }

    private Long id(String request) {
        return Long.parseLong(request);
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
        updateIfNotNull(material::setLesson,validationHelper.getIfExistsById(Lesson.class, id(request.getLessonId())));
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

    @Override
    public Material getFile(Long id) {
       return materialRepository.findById(id).orElse(null);
    }


}
