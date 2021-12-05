package com.lehre.course.repository;

import com.lehre.course.domain.Module;
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
    extends JpaRepository<Module, UUID>, JpaSpecificationExecutor<Module> {
    @EntityGraph(attributePaths = {"course"})
    Module findByTitle(String title);

    @Query(
        value = "select * from modules where course_id = :courseId and module_id = :moduleId",
        nativeQuery = true)
    Optional<Module> findModuleIntoCourse(
        @Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
