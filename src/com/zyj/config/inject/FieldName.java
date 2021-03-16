package com.zyj.config.inject;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface FieldName {
    /**
     * default extension name
     */
    String value() default "";

    int width() default 110;
}