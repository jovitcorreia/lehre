package com.lehre.course.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Rejecter {
    public static String[] rejectNullValues(Object source) {
        final BeanWrapper target = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptors = target.getPropertyDescriptors();
        Set<String> rejectedValues = new HashSet<>();
        for (PropertyDescriptor descriptor : descriptors) {
            Object valorAlvo = target.getPropertyValue(descriptor.getName());
            if (valorAlvo == null || valorAlvo == "") rejectedValues.add((descriptor.getName()));
        }
        String[] result = new String[rejectedValues.size()];
        return rejectedValues.toArray(result);
    }
}
