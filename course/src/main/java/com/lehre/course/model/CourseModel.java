package com.lehre.course.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lehre.course.constant.CourseLevel;
import com.lehre.course.constant.CourseStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
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
  private CourseStatus courseStatus;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseLevel courseLevel;

  @Column(nullable = false)
  private UUID instructor;

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
