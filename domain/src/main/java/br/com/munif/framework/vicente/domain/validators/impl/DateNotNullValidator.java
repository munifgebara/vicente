package br.com.munif.framework.vicente.domain.validators.impl;

import br.com.munif.framework.vicente.domain.validators.annotations.DateNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by willian on 20/07/17.
 */
public class DateNotNullValidator implements ConstraintValidator<DateNotNull, Date> {

    @Override
    public void initialize(DateNotNull dateNotNull) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return date != null;
    }
}
