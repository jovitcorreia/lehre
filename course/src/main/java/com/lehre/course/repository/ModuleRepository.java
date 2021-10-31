package com.lehre.course.repository;

import com.lehre.course.model.ModuleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository
    extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {
  @EntityGraph(attributePaths = {"course"})
  ModuleModel findByTitle(String title);

  @Query(
      value = "select * from tb_modules where course_id = :courseId and module_id = :moduleId",
      nativeQuery = true)
  Optional<ModuleModel> findModuleIntoCourse(
      @Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
