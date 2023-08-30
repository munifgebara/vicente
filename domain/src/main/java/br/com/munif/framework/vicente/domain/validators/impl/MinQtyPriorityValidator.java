package br.com.munif.framework.vicente.domain.validators.impl;

import br.com.munif.framework.vicente.domain.validators.annotations.MinQtyPriority;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by willian on 20/07/17.
 */
public class MinQtyPriorityValidator implements ConstraintValidator<MinQtyPriority, List<?>> {

    protected Object object;

    @Override
    public void initialize(MinQtyPriority minQtyPriority) {

    }

    @Override
    public boolean isValid(List<?> objects, ConstraintValidatorContext constraintValidatorContext) {

        if (objects == null || objects.size() <= 0) return true;

        int qtyPriority = 0;
        for (Object o : objects) {
            try {
                Field priority = o.getClass().getDeclaredField("priority");
                priority.setAccessible(true);
                if (priority.getBoolean(o)) {
                    object = o;
                    qtyPriority++;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return qtyPriority == 1;
    }

}
