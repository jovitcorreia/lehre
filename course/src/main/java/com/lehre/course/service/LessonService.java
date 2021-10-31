package com.lehre.course.service;

import com.lehre.course.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface LessonService {
  void save(LessonModel lessonModel);

  Page<LessonModel> findAll(Specification<LessonModel> spec, Pageable pageable);

  Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID courseId);

  void delete(LessonModel lessonModel);
}
