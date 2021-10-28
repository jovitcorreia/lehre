package com.lehre.course.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Entity
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Setter
@Table(name = "tb_modules")
@ToString
public class ModuleModel implements Serializable {
  public static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime creationDate;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @ToString.Exclude
  private CourseModel course;

  @Fetch(FetchMode.SUBSELECT)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
  @ToString.Exclude
  private Set<LessonModel> lessons;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || Hibernate.getClass(this) != Hibernate.getClass(object)) return false;
    ModuleModel moduleModel = (ModuleModel) object;
    return id != null && Objects.equals(id, moduleModel.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
