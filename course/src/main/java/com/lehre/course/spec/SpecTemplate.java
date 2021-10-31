package com.lehre.course.spec;

import com.lehre.course.model.CourseModel;
import com.lehre.course.model.LessonModel;
import com.lehre.course.model.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecTemplate {
  public static Specification<ModuleModel> modulesIntoCourse(final UUID courseId) {
    return (root, query, cb) -> {
      query.distinct(true);
      Root<CourseModel> course = query.from(CourseModel.class);
      Expression<Collection<ModuleModel>> modulesIntoCourse = course.get("modules");
      return cb.and(
          cb.equal(course.get("courseId"), courseId), cb.isMember(root, modulesIntoCourse));
    };
  }

  public static Specification<LessonModel> lessonsIntoModule(final UUID moduleId) {
    return (root, query, cb) -> {
      query.distinct(true);
      Root<ModuleModel> module = query.from(ModuleModel.class);
      Expression<Collection<LessonModel>> lessonsIntoCourse = module.get("lessons");
      return cb.and(
          cb.equal(module.get("moduleId"), moduleId), cb.isMember(root, lessonsIntoCourse));
    };
  }

  @And({
    @Spec(path = "level", spec = Equal.class),
    @Spec(path = "name", spec = Like.class),
    @Spec(path = "status", spec = Equal.class)
  })
  public interface CourseSpec extends Specification<CourseModel> {}

  @Spec(path = "title", spec = Like.class)
  public interface ModuleSpec extends Specification<ModuleModel> {}

  @Spec(path = "title", spec = Like.class)
  public interface LessonSpec extends Specification<LessonModel> {}
}
