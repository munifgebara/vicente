package br.com.munif.framework.vicente.domain.validators.annotations;

import br.com.munif.framework.vicente.domain.validators.impl.DateNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateNotNullValidator.class)
@Documented
public @interface DateNotNull {
    String message() default "{DateNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
