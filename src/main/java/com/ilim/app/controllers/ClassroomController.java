package com.ilim.app.controllers;

import com.ilim.app.business.services.ClassroomService;
import com.ilim.app.core.util.result.ApiResponse;
import com.ilim.app.dto.classroom.CreateClassroomRequest;
import com.ilim.app.dto.classroom.ClassroomResponse;
import com.ilim.app.dto.classroom.JoinClassroomRequest;
import com.ilim.app.dto.classroom.UpdateClassroomRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    //tested - 04
    @GetMapping
    public ResponseEntity<List<ClassroomResponse>> getAllClassrooms() {
        List<ClassroomResponse> classrooms = classroomService.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }
    //tested -04
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponse> getClassroomById(@PathVariable Long id) {
        ClassroomResponse classroom = classroomService.getClassroomById(id);
        return ResponseEntity.ok(classroom);
    }

    //tested -04
    @PutMapping
    public ResponseEntity<ClassroomResponse> updateClassroom(@Valid @RequestBody UpdateClassroomRequest request) {
        ClassroomResponse classroom = classroomService.updateClassroom(request.getId(), request);
        return ResponseEntity.ok(classroom);
    }

    //tested - 03
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }

    //tested - 04
    @PostMapping
    public ResponseEntity<ClassroomResponse> createClassroom(@Valid @RequestBody CreateClassroomRequest request) {
        return ResponseEntity.ok(classroomService.createClassroom(request));
    }

    //tested - 05
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<?>> joinClassroom(@Valid @RequestBody JoinClassroomRequest request) {
        ApiResponse<Void> response = classroomService.joinClassroom(request);
        return new ResponseEntity<>(response, response.isSuccess()
                ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    //tested - 04
    @GetMapping("/{classroomId}/students")
    public ResponseEntity<List<?>> listOfStudents(@Valid @PathVariable Long classroomId) {
        return ResponseEntity.ok(classroomService.getStudentsInClassroom(classroomId));
    }
}
