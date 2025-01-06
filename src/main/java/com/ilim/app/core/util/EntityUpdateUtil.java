package com.ilim.app.core.util;

import java.util.function.Consumer;

public class EntityUpdateUtil {
    public static <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null && !((String) value).trim().isEmpty()) {
            setter.accept(value);
        }
    }
}
