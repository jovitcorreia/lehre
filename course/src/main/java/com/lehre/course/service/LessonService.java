package com.lehre.course.service;

import com.lehre.course.data.LessonData;
import com.lehre.course.domain.Lesson;
import com.lehre.course.domain.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    @Transactional
    Lesson create(Module module, LessonData lessonData);

    Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID courseId);

    Page<Lesson> list(Specification<Lesson> spec, Pageable pageable);

    @Transactional
    Lesson update(LessonData lessonData, Lesson lesson);

    @Transactional
    void delete(Lesson lesson);
}
