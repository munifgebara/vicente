package br.com.munif.framework.vicente.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VicPublicOperation {
    boolean isPublic() default true;
}