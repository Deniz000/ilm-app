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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonValidationHelper validationHelper;
    private ModelMapperService modelMapperService;

    public LessonResponse createLesson(LessonRequest lessonRequest) {
        log.info("Create lesson: {}", lessonRequest.getTitle());
        if (validationHelper.validateLessonByTitle(lessonRequest.getTitle())) {
            throw new EntityAlreadyExits("Lesson already exists" + lessonRequest.getTitle());
        }
        Lesson lesson = modelMapperService.forRequest().map(lessonRequest, Lesson.class);
        lessonRepository.save(lesson);
        log.info("Lesson created: {}", lesson);
        return modelMapperService.forResponse().map(lesson, LessonResponse.class);

    }

    public LessonResponse getLessonById(Long id) {
        log.info("Getting Lesson by id: {}", id);
        Lesson lesson = validationHelper.getLessonIfExists(id);
        return modelMapperService.forResponse().map(lesson, LessonResponse.class);
    }

    public LessonResponse updateLesson(Long id, LessonUpdateRequest lessonRequest) {
        log.info("Updating calendar event with ID: {}", id);
        Lesson lesson = validationHelper.getLessonIfExists(id);
        modelMapperService.forRequest().map(lessonRequest, lesson);
        lesson.setCategory(validationHelper.getCategoryIfExists(id));
        lesson.setClassroom(validationHelper.getClassroomIfExists(id));
        lessonRepository.save(lesson);
        log.info("Calendar event updated with ID: {}", id);
        return modelMapperService.forResponse().map(lesson, LessonResponse.class);
    }


    @Override
    public List<LessonResponse> getAllLessons() {
        log.info("Getting all lessons");
        return lessonRepository.findAll().stream()
                .map(lesson -> modelMapperService.forResponse()
                        .map(lesson,LessonResponse.class))
                .collect(Collectors.toList());
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

