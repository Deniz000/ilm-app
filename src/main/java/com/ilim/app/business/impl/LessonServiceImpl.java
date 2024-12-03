package com.ilim.app.business.impl;

import com.ilim.app.business.services.LessonService;
import com.ilim.app.business.validationhelper.LessonValidationHelper;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dto.lesson.LessonRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import com.ilim.app.dto.lesson.LessonUpdateRequest;
import com.ilim.app.entities.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ilim.app.core.util.EntityUpdateUtil.updateIfNotNull;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonValidationHelper validationHelper;
    private final ModelMapperService modelMapperService;

    @Override
    @Transactional
    public LessonResponse createLesson(LessonRequest lessonRequest) {
        log.info("Creating lesson with title: {}", lessonRequest.getTitle());

        if (validationHelper.validateLessonByTitle(lessonRequest.getTitle())) {
            throw new EntityAlreadyExits("Lesson already exists: " + lessonRequest.getTitle());
        }
        Classroom classroom = validationHelper.getClassroomIfExists(lessonRequest.getClassroomId());
        Category category = validationHelper.getCategoryIfExists(lessonRequest.getCategoryId());
        UserEntity teacher = validationHelper.getUserIfExists(lessonRequest.getCallerId());
        Lesson lesson = new Lesson();
        modelMapperService.forRequest().map(lessonRequest, lesson);
        lesson.setClassroom(classroom);
        lesson.setCategory(category);
        lesson.setCaller(teacher);
        lessonRepository.save(lesson);
        log.error("Lesson created successfully: {}", lesson);
        return modelMapperService.forResponse().map(lesson,LessonResponse.class);
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        log.info("Getting all lessons");
        return lessonRepository.findAll().stream()
                .map(lesson -> modelMapperService.forResponse()
                        .map(lesson,LessonResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public LessonResponse getLessonById(Long id) {
        log.info("Getting Lesson by id: {}", id);
        Lesson lesson = validationHelper.getLessonIfExists(id);
        return modelMapperService.forResponse().map(lesson, LessonResponse.class);
    }

    @Override
    @Transactional
    public LessonResponse updateLesson(Long id, LessonUpdateRequest request) {
        log.info("Updating lesson with ID: {}", id);

        // İlgili dersin varlığını doğrula ve yükle
        Lesson lesson = validationHelper.getLessonIfExists(id);

        // Güncelleme yapılacak alanları kontrol et ve ata
        if (request.getCategoryId() != null) {
            Category category = validationHelper.getCategoryIfExists(request.getCategoryId());
            lesson.setCategory(category);
            log.info("Category updated for lesson ID: {}", id);
        }

        if (request.getCallerId() != null) {
            UserEntity caller = validationHelper.getUserIfExists(request.getCallerId());
            lesson.setCaller(caller);
            log.info("Caller updated for lesson ID: {}", id);
        }
        updateIfNotNull(lesson::setTitle, request.getTitle());
        updateIfNotNull(lesson::setContent, request.getContent());
        updateIfNotNull(lesson::setCallTime, request.getCallTime());
        updateIfNotNull(lesson::setCallLink, request.getCallLink());

        // Değişiklikleri kaydet
        lessonRepository.save(lesson);
        log.info("Lesson updated successfully with ID: {}", id);

        // Güncellenmiş dersi yanıt DTO'suna dönüştür
        return modelMapperService.forResponse().map(lesson, LessonResponse.class);
    }



    public List<LessonResponse> getLessonsForUser(Long userId) {
        log.info("Getting all lessons for user: {}", userId);
        UserEntity user = validationHelper.getUserIfExists(userId);
        return lessonRepository.findLessonsByUserId(user.getId()).stream()
                .map(lesson -> modelMapperService.forResponse()
                        .map(lesson,LessonResponse.class))
                .collect(Collectors.toList());
    }

    public List<LessonResponse> getLessonsForClassroom(Long classroomId) {
        log.info("Getting all lessons for classroom: {}", classroomId);
        Classroom classroom = validationHelper.getClassroomIfExists(classroomId);
        return lessonRepository.findLessonByClassroom_Id(classroom.getId()).stream()
                .map(lesson -> modelMapperService.forResponse().map(lesson,LessonResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteLesson(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Lesson lesson = validationHelper.getLessonIfExists(id);
        lessonRepository.deleteById(lesson.getId());
        log.info("Notification with ID: {} deleted.", id);

    }
//    public Lesson assignAdditionalTeacher(Long lessonId, Long teacherId) {
//        Lesson lesson = fetchLesson(lessonId);
//        UserEntity teacher = fetchUser(teacherId);
//
    /// /        if (teacher.getRoles().stream().noneMatch(role -> role.getName().equals(Role.RoleName.TEACHER))) {
    /// /            throw new UserNotTeacherException("User is not a teacher");
    /// /        }
//
//        // Öğretmeni dersin katılımcı listesine ekle
//        lesson.getParticipants().add(teacher);
//        return lessonRepository.save(lesson);
//    }



}

