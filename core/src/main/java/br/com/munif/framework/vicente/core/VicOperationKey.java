package br.com.munif.framework.vicente.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VicOperationKey {
    String value();

    boolean basic() default false;
}