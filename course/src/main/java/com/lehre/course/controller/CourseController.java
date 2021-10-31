package com.lehre.course.controller;

import com.lehre.course.data.CourseData;
import com.lehre.course.mapper.CourseMapper;
import com.lehre.course.model.CourseModel;
import com.lehre.course.service.CourseService;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/courses")
@RestController
public class CourseController {
  private final CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @PostMapping
  public ResponseEntity<?> store(@RequestBody CourseData courseData) {
    var courseModel = new CourseMapper().from(courseData);
    courseService.save(courseModel);
    courseModel.add(
        linkTo(methodOn(CourseController.class).show(courseModel.getCourseId())).withSelfRel());
    return ResponseEntity.status(HttpStatus.CREATED).body(courseModel);
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<?> show(@PathVariable UUID courseId) {
    Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
    if (courseModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("Course with id %s not found!", courseId));
    }
    CourseModel courseModel = courseModelOptional.get();
    courseModel.add(
        linkTo(methodOn(CourseController.class).show(courseModel.getCourseId())).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(courseModel);
  }

  @GetMapping
  public ResponseEntity<Page<CourseModel>> index(
      @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<CourseModel> courseModelPage = courseService.findAll(pageable);
    if (!courseModelPage.isEmpty()) {
      for (CourseModel course : courseModelPage.toList()) {
        course.add(linkTo(methodOn(CourseController.class).show(course.getCourseId())).withSelfRel());
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(courseModelPage);
  }

  @PutMapping("/{courseId}")
  public ResponseEntity<?> update(
      @PathVariable UUID courseId, @RequestBody @Validated CourseData courseData) {
    Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
    if (courseModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("Course with id %s not found!", courseId));
    }
    CourseModel courseModel = courseModelOptional.get();
    BeanUtils.copyProperties(courseData, courseModel);
    courseService.save(courseModel);
    courseModel.add(
        linkTo(methodOn(CourseController.class).show(courseModel.getCourseId())).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(courseModel);
  }

  @DeleteMapping("/{courseId}")
  public ResponseEntity<?> delete(@PathVariable UUID courseId) {
    Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
    if (courseModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("Course with id %s not found!", courseId));
    }
    CourseModel courseModel = courseModelOptional.get();
    courseService.delete(courseModel);
    return ResponseEntity.status(HttpStatus.OK)
        .body(String.format("Course  with id %s deleted  successfully!", courseId));
  }
}
