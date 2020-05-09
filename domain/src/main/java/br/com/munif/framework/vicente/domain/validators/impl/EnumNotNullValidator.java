package br.com.munif.framework.vicente.domain.validators.impl;

import br.com.munif.framework.vicente.domain.validators.annotations.EnumNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by willian on 20/07/17.
 */
public class EnumNotNullValidator implements ConstraintValidator<EnumNotNull, Enum<?>> {
    @Override
    public void initialize(EnumNotNull enumNotNull) {

    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        return anEnum != null;
    }
}
