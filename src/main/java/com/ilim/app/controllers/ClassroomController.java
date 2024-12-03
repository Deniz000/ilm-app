package com.ilim.app.controllers;

import com.ilim.app.business.services.ClassroomService;
import com.ilim.app.dto.classroom.CreateClassroomRequest;
import com.ilim.app.dto.classroom.ClassroomResponse;
import com.ilim.app.dto.classroom.JoinClassroomRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    //tested - 03
    @PostMapping
    public ResponseEntity<ClassroomResponse> createClassroom(@Valid @RequestBody CreateClassroomRequest request) {
        return ResponseEntity.ok(classroomService.createClassroom(request));
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinClassroom(@Valid @RequestBody JoinClassroomRequest request) {
        classroomService.joinClassroom(request);
        return ResponseEntity.noContent().build();
    }

    //tested - 03
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{classroomId}/students")
    public ResponseEntity<List<?>> listStudents(@Valid @PathVariable Long classroomId) {
        return ResponseEntity.ok(classroomService.listStudents(classroomId));
    }
}
