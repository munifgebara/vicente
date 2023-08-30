package br.com.munif.framework.vicente.core.phonetics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author munif
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VicPhoneticPolicy {
    String field() default "name";

    String phoneticField() default "phonetic";
}
