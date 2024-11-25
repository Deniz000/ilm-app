package com.ilim.app.business.impl;

import com.ilim.app.business.services.LessonService;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.business.dto.lesson.LessonRequest;
import com.ilim.app.business.dto.lesson.LessonResponse;
import com.ilim.app.business.dto.lesson.LessonUpdateRequest;
import com.ilim.app.entities.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonValidationHelper validationHelper;

    public LessonResponse createLesson(LessonRequest lessonRequest) {
        if (validationHelper.exitsLessonByTitle(lessonRequest.getTitle())) {
            throw new EntityAlreadyExits("Lesson already exists" + lessonRequest.getTitle());
        }
        Lesson lesson = mapToEntity(lessonRequest);
        return mapToResponse(lessonRepository.save(lesson));

    }

    public LessonResponse getLessonById(Long id) {
        Lesson lesson = validationHelper.getIfLessonExists(id);
        return mapToResponse(lesson);
    }

    public LessonResponse updateLesson(Long id, LessonUpdateRequest lessonRequest) {
        Lesson lesson = validationHelper.getIfLessonExists(id);
        lesson.setCategory(validationHelper.getIfCategoryExists(lessonRequest.getCategoryId()));
        lesson.setContent(lessonRequest.getContent());
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setCallTime(lessonRequest.getCallTime());
        lesson.setCallLink(lessonRequest.getCallLink());
        lesson.setClassroom(validationHelper.getIfClassroomExists(lessonRequest.getClassroomId()));
        //app 'den default alınacak
        return mapToResponse(lessonRepository.save(lesson));
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new EntityAlreadyExits("Lesson does not exists" + id);
        }
        lessonRepository.deleteById(id);
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        return lessonRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LessonResponse> getLessonsForUser(Long userId) {
        UserEntity user = validationHelper.getUserIfExists(userId);
        return lessonRepository.findLessonsByUserId(user.getId()).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LessonResponse> getLessonsForClassroom(Long classroomId) {
        Classroom classroom = validationHelper.getIfClassroomExists(classroomId);
        return lessonRepository.findLessonByClassroom_Id(classroom.getId()).stream().map(this::mapToResponse).collect(Collectors.toList());
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
    private LessonResponse mapToResponse(Lesson lesson) {
        return new LessonResponse(validationHelper.getIfCategoryExists(lesson.getCategory().getId()).getName(), validationHelper.getUserIfExists(lesson.getCaller().getId()).getUsername(), lesson.getTitle(), lesson.getContent(), lesson.getCallTime(), lesson.getDuration(), lesson.getCallLink(), lesson.getClassroom().getName());
    }

    private Lesson mapToEntity(LessonRequest request) {
        Lesson lesson = new Lesson();
        lesson.setCategory(validationHelper.getIfCategoryExists(request.getCategoryId()));
        lesson.setCaller(validationHelper.getUserIfExists(request.getCallerId()));
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setDuration(request.getDuration());
        lesson.setCallLink(request.getCallLink());
        lesson.setClassroom(validationHelper.getIfClassroomExists(request.getClassroomId()));
        return lesson;
    }


}

