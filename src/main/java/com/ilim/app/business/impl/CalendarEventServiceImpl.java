package com.ilim.app.business.impl;

import com.ilim.app.business.services.CalendarEventService;
import com.ilim.app.business.validationhelper.CalendarEventValidator;
import com.ilim.app.business.validationhelper.ValidationHelper;
import com.ilim.app.core.exceptions.EntityAlreadyExits;
import com.ilim.app.core.util.mapper.ModelMapperService;
import com.ilim.app.dataAccess.CalendarEventRepository;
import com.ilim.app.dto.calendar.CalendarEventResponse;
import com.ilim.app.dto.calendar.CreateCalendarEventRequest;
import com.ilim.app.dto.calendar.UpdateCalendarEventRequest;
import com.ilim.app.entities.CalendarEvent;
import com.ilim.app.entities.CalendarEvent.EventType;
import com.ilim.app.entities.Lesson;
import com.ilim.app.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ilim.app.core.util.EntityUpdateUtil.updateIfNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final ModelMapperService modelMapperService;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional
    public CalendarEventResponse createCalendarEvent(CreateCalendarEventRequest request) {
        log.info("Creating new calendar event: {}", request.getTitle());
        if (validationHelper.validateByName(CalendarEvent.class, request.getTitle())) {
            throw new EntityAlreadyExits("This event title already exists");
        }
        if (calendarEventRepository.existsByStartTime(request.getStartTime())) {
            throw new EntityAlreadyExits("This event already exists. Change start time");
        }

        Lesson lesson = validationHelper.getIfExistsById(Lesson.class, request.getLessonId());
        UserEntity creator = validationHelper.getIfExistsById(UserEntity.class, request.getCreatorId());

        CalendarEvent event = new CalendarEvent();
        modelMapperService.forRequest().map(request, CalendarEvent.class);
        event.setLesson(lesson);
        event.setCreator(creator);
        calendarEventRepository.save(event);
        log.info("Calendar event created with ID: {}", event.getId());
        return modelMapperService.forResponse().map(event, CalendarEventResponse.class);
    }

    @Override
    @Transactional
    public CalendarEventResponse updateCalendarEvent(Long id, UpdateCalendarEventRequest request) {
        log.info("Updating calendar event with ID: {}", id);
        CalendarEvent event = validationHelper.getIfExistsById(CalendarEvent.class, id);
        updateIfNotNull(event::setTitle, request.getTitle());
        updateIfNotNull(event::setDescription, request.getDescription());
        updateIfNotNull(event::setEventType, EventType.fromString(request.getEventType()));
        updateIfNotNull(event::setStartTime, request.getStartTime());
        updateIfNotNull(event::setEndTime, request.getEndTime());
        updateIfNotNull(event::setLesson, event.getLesson());
        calendarEventRepository.save(event);
        log.info("Calendar event updated with ID: {}", id);
        return modelMapperService.forResponse().map(event, CalendarEventResponse.class);
    }


    @Override
    @Transactional
    public void deleteCalendarEvent(Long id) {
        log.info("Deleting calendar event with ID: {}", id);
        CalendarEvent event = validationHelper.getIfExistsById(CalendarEvent.class, id);
        calendarEventRepository.delete(event);
        log.info("Calendar event with ID: {} successfully deleted.", id);
    }

    @Override
    public List<CalendarEventResponse> getAllEventsByTeacher(Long teacherId) {
        CalendarEventValidator calendarEventValidator = validationHelper.getEventsValidator();
        List<CalendarEvent> events = calendarEventValidator.getCalendarEventsOfTeacher(teacherId);
        return events.stream()
                .map(event->modelMapperService.forResponse()
                        .map(event,CalendarEventResponse.class)).toList();
    }

    @Override
    public List<CalendarEventResponse> getAllEventsByStudent(Long studentId) {
        CalendarEventValidator calendarEventValidator = validationHelper.getEventsValidator();
        List<CalendarEvent> events = calendarEventValidator.getCalendarEventsOfStudent(studentId);
        return events.stream()
                .map(event->modelMapperService.forResponse()
                        .map(event,CalendarEventResponse.class)).toList();
    }

    @Override
    public List<CalendarEventResponse> getFilteredEvents(String day, String week, Boolean upcoming) {
        log.info("Fetching filtered events");
        return calendarEventRepository.findAll((Sort) Specification.where(
                        CalendarEventSpecifications.filterByDay(day)
                                .and(CalendarEventSpecifications.filterByWeek(week))
                                .and(CalendarEventSpecifications.filterUpcoming(upcoming))
                )).stream()
                .map(event -> modelMapperService.forResponse().map(event, CalendarEventResponse.class))
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<CalendarEventResponse> getEventsByLesson(Long lessonId) {
        log.info("Fetching events for lesson ID: {}", lessonId);
        CalendarEventValidator calendarEventValidator = validationHelper.getEventsValidator();
        List<CalendarEvent> events = calendarEventValidator.getCalendarEventsByLessonId(lessonId);
        return events.stream().map(
                event -> modelMapperService.forResponse()
                        .map(event, CalendarEventResponse.class)
        ).toList();
    }

}
