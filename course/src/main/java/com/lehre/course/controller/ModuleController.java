package com.lehre.course.controller;

import com.lehre.course.data.ModuleData;
import com.lehre.course.domain.Course;
import com.lehre.course.domain.Module;
import com.lehre.course.repository.SpecTemplate;
import com.lehre.course.service.CourseService;
import com.lehre.course.service.ModuleService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestController
public class ModuleController {
    private static final String COURSE_NOT_FOUND = "Course with id %s not found";
    private static final String MODULE_NOT_FOUND = "Module not found for this course";
    private final CourseService courseService;
    private final ModuleService moduleService;

    @Autowired
    public ModuleController(CourseService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> store(
        @PathVariable UUID courseId, @RequestBody @Valid ModuleData moduleData) {
        log.debug("POST store moduleData {}", moduleData.toString());
        Optional<Course> courseOptional = courseService.find(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(COURSE_NOT_FOUND, courseId));
        }
        Module module = moduleService.create(courseOptional.get(), moduleData);
        log.info("Module created successfully with id {}", module.getModuleId());
        return ResponseEntity.status(HttpStatus.CREATED).body(module);
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> index(
        @PathVariable UUID courseId,
        SpecTemplate.ModuleSpec spec,
        @PageableDefault(sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {
        Optional<Course> courseOptional = courseService.find(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(COURSE_NOT_FOUND, courseId));
        }
        Page<Module> modulePage =
            moduleService.list(SpecTemplate.modulesIntoCourse(courseId).and(spec), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(modulePage);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> show(
        @PathVariable(value = "courseId") UUID courseId,
        @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MODULE_NOT_FOUND);
        }
        Module module = moduleOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(module);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> update(
        @RequestBody ModuleData moduleData,
        @PathVariable UUID courseId,
        @PathVariable UUID moduleId) {
        log.debug("PUT update moduleData {}", moduleData.toString());
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MODULE_NOT_FOUND);
        }
        Module module = moduleService.update(moduleData, moduleOptional.get());
        log.info("Module with id {} updated successfully", module.getModuleId());
        return ResponseEntity.status(HttpStatus.OK).body(module);
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> delete(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        log.debug("DELETE delete module with id {}", moduleId);
        Optional<Module> moduleOptional =
            moduleService.findModuleIntoCouse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MODULE_NOT_FOUND);
        }
        Module module = moduleOptional.get();
        moduleService.delete(module);
        log.info("Module  with id {} deleted successfully", moduleId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(String.format("Module with id %s deleted successfully", moduleId));
    }
}
