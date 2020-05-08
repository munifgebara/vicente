package br.com.munif.framework.vicente.domain.validators.annotations;

import br.com.munif.framework.vicente.domain.validators.impl.StringNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringNotNullValidator.class)
@Documented
public @interface StringNotNull {
    String message() default "{StringNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
