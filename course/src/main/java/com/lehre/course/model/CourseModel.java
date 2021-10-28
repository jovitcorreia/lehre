package com.lehre.course.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lehre.course.constant.CourseLevel;
import com.lehre.course.constant.CourseStatus;
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
@Table(name = "tb_courses")
@ToString
public class CourseModel implements Serializable {
  public static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  private String imageUrl;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime creationDate;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime lastUpdateDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseStatus status;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseLevel level;

  @Column(nullable = false)
  private UUID instructor;

  @Fetch(FetchMode.SUBSELECT)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
  @ToString.Exclude
  private Set<ModuleModel> modules;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || Hibernate.getClass(this) != Hibernate.getClass(object)) return false;
    CourseModel courseModel = (CourseModel) object;
    return id != null && Objects.equals(id, courseModel.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
