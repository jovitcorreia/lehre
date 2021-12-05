package com.lehre.course.repository;

import com.lehre.course.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository
    extends JpaRepository<Lesson, UUID>, JpaSpecificationExecutor<Lesson> {
    @Query(
        value = "select * from lessons where module_id = :moduleId and lesson_id = :lessonId",
        nativeQuery = true)
    Optional<Lesson> findLessonIntoModule(
        @Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);
}
