package com.ilim.app.business.impl;

import com.ilim.app.business.services.LessonService;
import com.ilim.app.business.validationhelper.CalendarEventValidator;
import com.ilim.app.business.validationhelper.MaterialValidator;
import com.ilim.app.business.validationhelper.NoteValidator;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.LessonRepository;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.lesson.LessonRequest;
import com.ilim.app.dto.lesson.LessonResponse;
import com.ilim.app.dto.lesson.LessonUpdateRequest;
import com.ilim.app.dto.material.MaterialResponse;
import com.ilim.app.dto.note.NoteResponse;
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
    private final ModelMapperService modelMapperService;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional
    public LessonResponse createLesson(LessonRequest lessonRequest) {
        log.info("Creating lesson with title: {}", lessonRequest.getTitle());

        if (validationHelper.validateByName(Lesson.class, lessonRequest.getTitle())) {
            throw new EntityAlreadyExits("Lesson already exists: " + lessonRequest.getTitle());
        }
        Classroom classroom = validationHelper.getIfExistsById(Classroom.class, lessonRequest.getClassroomId());
        Category category = validationHelper.getIfExistsById(Category.class, lessonRequest.getCategoryId());
        UserEntity teacher = validationHelper.getIfExistsById(UserEntity.class, lessonRequest.getCallerId());
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
        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, id);
        return modelMapperService.forResponse().map(lesson, LessonResponse.class);
    }

    @Override
    @Transactional
    public LessonResponse updateLesson(Long id, LessonUpdateRequest request) {
        log.info("Updating lesson with ID: {}", id);

        // İlgili dersin varlığını doğrula ve yükle
        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, id);

        // Güncelleme yapılacak alanları kontrol et ve ata
        if (request.getCategoryId() != null) {
            Category category = validationHelper.getIfExistsById(Category.class, request.getCategoryId());
            lesson.setCategory(category);
            log.info("Category updated for lesson ID: {}", id);
        }

        if (request.getCallerId() != null) {
            UserEntity caller = validationHelper.getIfExistsById(UserEntity.class, request.getCallerId());
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
        UserEntity user = validationHelper.getIfExistsById(UserEntity.class, userId);
        return lessonRepository.findLessonsByUserId(user.getId()).stream()
                .map(lesson -> modelMapperService.forResponse()
                        .map(lesson,LessonResponse.class))
                .toList();
    }

    @Override
    public List<MaterialResponse> getMaterialsByLessonId(Long id) {
        MaterialValidator materialValidator = validationHelper.getMaterialsValidator();
        List<Material> materials = materialValidator.getMaterialsByLessonId(id);
        return materials.stream().map(
                material ->modelMapperService.forResponse().map(material, MaterialResponse.class)
        ).toList();
    }

    @Override
    public List<NoteResponse> getNotesByLessonId(Long id) {
        NoteValidator noteValidator = validationHelper.getNotesValidator();
        List<Note> notes = noteValidator.getNotesByLessonId(id);
        return notes.stream().map(
                note -> modelMapperService.forResponse().map(note, NoteResponse.class)
        ).toList();
    }

    @Override
    public List<CalendarEventResponse> getCalendarByLessonId(Long id) {
        CalendarEventValidator calendarEventValidator = validationHelper.getEventsValidator();
        List<CalendarEvent> calendars = calendarEventValidator.getCalendarEventsByLessonId(id);
        return calendars.stream()
                .map(calendar -> modelMapperService.forResponse().map(calendar, CalendarEventResponse.class))
                .toList();
    }


    @Override
    public void deleteLesson(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, id);
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

