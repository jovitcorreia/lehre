package com.lehre.course.util;

public interface Mapper<Source, Target> {
  Target from(Source source);
}
