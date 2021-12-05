package com.lehre.course.controller;

import com.lehre.course.data.LessonData;
import com.lehre.course.domain.Lesson;
import com.lehre.course.domain.Module;
import com.lehre.course.repository.SpecTemplate;
import com.lehre.course.service.LessonService;
import com.lehre.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LessonController {
    private final LessonService lessonService;
    private final ModuleService moduleService;

    @Autowired
    public LessonController(LessonService lessonService, ModuleService moduleService) {
        this.lessonService = lessonService;
        this.moduleService = moduleService;
    }

    @PostMapping("/courses/{courseId}/modules/{moduleId}/lessons")
    public ResponseEntity<Object> store(
        @PathVariable UUID courseId,
        @PathVariable UUID moduleId,
        @RequestBody @Valid LessonData lessonData) {
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
        }
        Lesson lesson = lessonService.create(moduleOptional.get(), lessonData);
        return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}/lessons")
    public ResponseEntity<Object> index(
        @PathVariable UUID courseId,
        @PathVariable UUID moduleId,
        SpecTemplate.LessonSpec spec,
        @PageableDefault(sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
        }
        Page<Lesson> lessonPage =
            lessonService.list(SpecTemplate.lessonsIntoModule(moduleId).and(spec), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(lessonPage);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> show(
        @PathVariable UUID courseId, @PathVariable UUID moduleId, @PathVariable UUID lessonId) {
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
        }
        Optional<Lesson> lessonOptional =
            lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        Lesson lesson = lessonOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> update(
        @PathVariable UUID courseId,
        @PathVariable UUID moduleId,
        @PathVariable UUID lessonId,
        @RequestBody LessonData lessonData) {
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
        }
        Optional<Lesson> lessonOptional =
            lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        Lesson lesson = lessonService.update(lessonData, lessonOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> delete(
        @PathVariable UUID courseId, @PathVariable UUID moduleId, @PathVariable UUID lessonId) {
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
        }
        Optional<Lesson> lessonOptional =
            lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        lessonService.delete(lessonOptional.get());
        return ResponseEntity.status(HttpStatus.OK)
            .body(String.format("Lesson with id %s deleted successfully.", lessonId));
    }
}
