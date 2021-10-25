package com.lehre.authuser.spec;

import com.lehre.authuser.model.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecTemplate {
  @And({
    @Spec(path = "email", spec = Like.class),
    @Spec(path = "type", spec = Equal.class),
    @Spec(path = "status", spec = Equal.class)
  })
  public interface UserSpec extends Specification<UserModel> {}
}
