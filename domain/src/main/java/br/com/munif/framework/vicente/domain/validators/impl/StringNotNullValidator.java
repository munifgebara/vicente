package br.com.munif.framework.vicente.domain.validators.impl;

import br.com.munif.framework.vicente.domain.validators.annotations.StringNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by willian on 20/07/17.
 */
public class StringNotNullValidator implements ConstraintValidator<StringNotNull, String> {

    @Override
    public void initialize(StringNotNull stringNotNull) {
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return string != null && !string.equals("");
    }
}
