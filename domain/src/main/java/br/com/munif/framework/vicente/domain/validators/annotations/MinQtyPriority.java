package br.com.munif.framework.vicente.domain.validators.annotations;

import br.com.munif.framework.vicente.domain.validators.impl.MinQtyPriorityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinQtyPriorityValidator.class)
@Documented
public @interface MinQtyPriority {

    String message() default "{MinQtyPriority.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
