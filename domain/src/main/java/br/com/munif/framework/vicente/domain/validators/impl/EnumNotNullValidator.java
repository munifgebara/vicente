package br.com.munif.framework.vicente.domain.validators.impl;

import br.com.gileade.gileadeweb.domain.model.validators.annotations.EnumNotNull;
import io.gumga.core.GumgaThreadScope;

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

        if (GumgaThreadScope.configuration.get() != null) {
            Boolean desabilitaCamposObrigatorios = (Boolean) GumgaThreadScope.configuration.get().get("desabilitaCamposObrigatorios");
            if (desabilitaCamposObrigatorios != null && desabilitaCamposObrigatorios) {
                return true;
            }
        }

        return anEnum != null;
    }
}
