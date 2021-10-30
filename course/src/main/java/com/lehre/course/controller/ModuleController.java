package com.lehre.course.controller;

import com.lehre.course.data.ModuleData;
import com.lehre.course.model.CourseModel;
import com.lehre.course.model.ModuleModel;
import com.lehre.course.service.CourseService;
import com.lehre.course.service.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ModuleController {
  private final CourseService courseService;
  private final ModuleService moduleService;

  @Autowired
  public ModuleController(CourseService courseService, ModuleService moduleService) {
    this.courseService = courseService;
    this.moduleService = moduleService;
  }

  @PostMapping("/courses/{courseId}/modules/{moduleId}")
  public ResponseEntity<?> store(
      @PathVariable UUID courseId, @RequestBody @Valid ModuleData moduleData) {
    Optional<CourseModel> courseModelOptional = courseService.find(courseId);
    if (courseModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("Course with id %s not found.", courseId));
    }
    CourseModel courseModel = courseModelOptional.get();
    var moduleModel = new ModuleModel();
    BeanUtils.copyProperties(moduleData, moduleModel);
    moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
    moduleModel.setCourse(courseModel);
    moduleService.save(moduleModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(moduleModel);
  }

  @PutMapping("/courses/{courseId}/modules/{moduleId}")
  public ResponseEntity<?> update(
      @RequestBody ModuleData moduleData,
      @PathVariable UUID courseId,
      @PathVariable UUID moduleId) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
    }
    ModuleModel moduleModel = moduleModelOptional.get();
    BeanUtils.copyProperties(moduleData, moduleModel);
    moduleService.save(moduleModel);
    return ResponseEntity.status(HttpStatus.OK).body(moduleData);
  }

  @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
  public ResponseEntity<?> delete(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
    Optional<ModuleModel> moduleModelOptional =
        moduleService.findModuleIntoCouse(courseId, moduleId);
    if (moduleModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
    }
    ModuleModel moduleModel = moduleModelOptional.get();
    moduleService.delete(moduleModel);
    return ResponseEntity.status(HttpStatus.OK)
        .body(String.format("Module with id %s deleted successfully.", moduleId));
  }
}
