package com.lehre.authuser.util;

public interface Mapper<Source, Target> {
  Target from(Source source);
}
