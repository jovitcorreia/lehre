package com.lehre.course.controller;

import com.lehre.course.data.LessonData;
import com.lehre.course.model.LessonModel;
import com.lehre.course.model.ModuleModel;
import com.lehre.course.service.LessonService;
import com.lehre.course.service.ModuleService;
import com.lehre.course.spec.SpecTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
  public ResponseEntity<?> store(
      @PathVariable UUID courseId,
      @PathVariable UUID moduleId,
      @RequestBody @Valid LessonData lessonData) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
    }
    ModuleModel moduleModel = moduleModelOptional.get();
    var lessonModel = new LessonModel();
    BeanUtils.copyProperties(lessonData, lessonModel);
    lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
    lessonModel.setModule(moduleModel);
    lessonService.save(lessonModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(lessonModel);
  }

  @GetMapping("/courses/{courseId}/modules/{moduleId}/lessons")
  public ResponseEntity<?> index(
      @PathVariable UUID courseId,
      @PathVariable UUID moduleId,
      SpecTemplate.LessonSpec spec,
      @PageableDefault(sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
    }
    Page<LessonModel> lessonModelPage =
        lessonService.findAll(SpecTemplate.lessonsIntoModule(moduleId).and(spec), pageable);
    return ResponseEntity.status(HttpStatus.OK).body(lessonModelPage);
  }

  @GetMapping("/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}")
  public ResponseEntity<?> show(
      @PathVariable UUID courseId, @PathVariable UUID moduleId, @PathVariable UUID lessonId) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
    }
    Optional<LessonModel> lessonModelOptional =
        lessonService.findLessonIntoModule(moduleId, lessonId);
    if (lessonModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
    }
    var lessonModel = lessonModelOptional.get();
    return ResponseEntity.status(HttpStatus.OK).body(lessonModel);
  }

  @PutMapping("/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}")
  public ResponseEntity<?> update(
      @PathVariable UUID courseId,
      @PathVariable UUID moduleId,
      @PathVariable UUID lessonId,
      @RequestBody LessonData lessonData) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
    }
    Optional<LessonModel> lessonModelOptional =
        lessonService.findLessonIntoModule(moduleId, lessonId);
    if (lessonModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
    }
    LessonModel lessonModel = lessonModelOptional.get();
    BeanUtils.copyProperties(lessonData, lessonModel);
    lessonService.save(lessonModel);
    return ResponseEntity.status(HttpStatus.OK).body(lessonModel);
  }

  @DeleteMapping("/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}")
  public ResponseEntity<?> delete(
      @PathVariable UUID courseId, @PathVariable UUID moduleId, @PathVariable UUID lessonId) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course or module not found.");
    }
    Optional<LessonModel> lessonModelOptional =
        lessonService.findLessonIntoModule(moduleId, lessonId);
    if (lessonModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
    }
    LessonModel lessonModel = lessonModelOptional.get();
    lessonService.delete(lessonModel);
    return ResponseEntity.status(HttpStatus.OK)
        .body(String.format("Lesson with id %s deleted successfully.", lessonId));
  }
}
