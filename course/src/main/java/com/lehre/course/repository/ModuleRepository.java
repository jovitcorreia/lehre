package com.lehre.course.repository;

import com.lehre.course.model.ModuleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {
  @EntityGraph(attributePaths = {"course"})
  ModuleModel findByTitle(String title);
}
