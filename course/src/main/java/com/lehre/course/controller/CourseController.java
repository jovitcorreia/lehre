package com.lehre.course.controller;

import com.lehre.course.data.CourseData;
import com.lehre.course.domain.Course;
import com.lehre.course.service.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RequestMapping("/courses")
@RestController
public class CourseController {
    private static final String COURSE_NOT_FOUND = "Course with id %s not found";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody CourseData courseData) {
        log.debug("POST store courseData {}", courseData.toString());
        Course course = courseService.create(courseData);
        log.info("Course created successfully with id {}", course.getCourseId());
        course.add(
            linkTo(methodOn(CourseController.class).show(course.getCourseId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> show(@PathVariable UUID courseId) {
        Optional<Course> courseOptional = courseService.find(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(COURSE_NOT_FOUND, courseId));
        }
        Course course = courseOptional.get();
        course.add(
            linkTo(methodOn(CourseController.class).show(course.getCourseId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @GetMapping
    public ResponseEntity<Page<Course>> index(
        @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Course> coursePage = courseService.list(pageable);
        if (!coursePage.isEmpty()) {
            for (Course course : coursePage.toList()) {
                course.add(linkTo(methodOn(CourseController.class).show(course.getCourseId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(coursePage);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> update(
        @PathVariable UUID courseId, @RequestBody @Validated CourseData courseData) {
        log.debug("PUT update courseData {}", courseData.toString());
        Optional<Course> courseOptional = courseService.find(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(COURSE_NOT_FOUND, courseId));
        }
        Course course = courseService.update(courseData, courseOptional.get());
        log.info("Course with id {} updated successfully", course.getCourseId());
        course.add(
            linkTo(methodOn(CourseController.class).show(course.getCourseId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> delete(@PathVariable UUID courseId) {
        log.debug("DELETE delete course with id {}", courseId);
        Optional<Course> courseOptional = courseService.find(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(COURSE_NOT_FOUND, courseId));
        }
        courseService.delete(courseOptional.get());
        log.info("Course  with id {} deleted successfully", courseId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(String.format("Course  with id %s deleted  successfully", courseId));
    }
}
