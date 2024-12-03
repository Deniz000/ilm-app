package com.ilim.app.business.impl;

import com.ilim.app.business.services.CalendarEventService;
import com.ilim.app.business.validationhelper.CalendarEventValidationHelper;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.CalendarEventRepository;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;
import com.ilim.app.entities.CalendarEvent;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final ModelMapperService modelMapperService;
    private final CalendarEventValidationHelper validate;

    @Override
    @Transactional(readOnly = true)
    public List<CalendarEventResponse> getEventsByUser(Long userId) {
        log.info("Fetching events for user ID: {}", userId);
        validate.validateUserExists(userId);

        return calendarEventRepository.findByCreatorId(userId).stream()
                .map(event -> modelMapperService.forResponse().map(event, CalendarEventResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarEventResponse> getEventsByLesson(Long lessonId) {
        log.info("Fetching events for lesson ID: {}", lessonId);
        validate.validateLessonExists(lessonId);

        return calendarEventRepository.findCalendarEventByLessonId(lessonId).stream()
                .map(event -> modelMapperService.forResponse().map(event, CalendarEventResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CalendarEventResponse createCalendarEvent(CreateCalendarEventRequest request) {
        log.info("Creating new calendar event: {}", request.getTitle());

        validate.validateEventRequest(request);
        Lesson lesson = validate.getLessonIfExists(request.getLessonId());
        UserEntity creator = validate.getUserIfExists(request.getCreatorId());

        CalendarEvent event = modelMapperService.forRequest().map(request, CalendarEvent.class);
        event.setLesson(lesson);
        event.setCreator(creator);
        log.info("Calendar event created with ID: {}", event.getId());
        calendarEventRepository.save(event);
        return modelMapperService.forResponse().map(event, CalendarEventResponse.class);
    }
    @Override
    @Transactional
    public CalendarEventResponse updateCalendarEvent(Long id, UpdateCalendarEventRequest request) {
        log.info("Updating calendar event with ID: {}", id);
        CalendarEvent event = validate.getEventIfExists(id);
        //check point whether is null
        validate.validateAndUpdateEvent(event, request);

        calendarEventRepository.save(event);
        log.info("Calendar event updated with ID: {}", id);
        return modelMapperService.forResponse().map(event, CalendarEventResponse.class);
    }


    @Override
    @Transactional
    public void deleteCalendarEvent(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        CalendarEvent event = validate.getEventIfExists(id);
        calendarEventRepository.delete(event);
        log.info("Calendar event with ID: {} successfully deleted.", id);
    }
}
