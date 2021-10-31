package com.lehre.course.repository;

import com.lehre.course.model.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository
    extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {
  @Query(
      value = "select * from tb_lessons where module_id = :module_id and lesson_id = :lessonId",
      nativeQuery = true)
  Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);
}
