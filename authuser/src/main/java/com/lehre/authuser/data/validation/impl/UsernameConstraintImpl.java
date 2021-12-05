package com.lehre.authuser.data.validation.impl;

import com.lehre.authuser.data.validation.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return (username != null && !username.contains(" "));
    }
}
