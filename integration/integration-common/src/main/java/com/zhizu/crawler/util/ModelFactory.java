package com.zhizu.crawler.util;

import java.lang.reflect.Field;

public class ModelFactory {
    public static <T> T set(T t, boolean ok) {
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (Boolean.class.isAssignableFrom(field.getType())) {
                try {
                    field.set(t, ok);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    public static <T> T set(T t, String ok) {
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (Boolean.class.isAssignableFrom(field.getType())) {
                try {
                    field.set(t, ok);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }
}
