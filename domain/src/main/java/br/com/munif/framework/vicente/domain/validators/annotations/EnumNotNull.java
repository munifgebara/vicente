package br.com.munif.framework.vicente.domain.validators.annotations;

import br.com.munif.framework.vicente.domain.validators.impl.EnumNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumNotNullValidator.class)
@Documented
public @interface EnumNotNull {
    String message() default "{EnumNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
