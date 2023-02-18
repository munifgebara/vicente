package br.com.munif.framework.vicente.api.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author munif
 * View Model for transferring error message with a list of field errors.
 */
public class VicError implements Serializable {

    public final String className = "VicError";
    private static final long serialVersionUID = 1L;

    private final String code;
    private final String details;


    private List<FieldError> fieldErrors;

    public VicError(String code) {
        this(code, null);
    }

    public VicError(String code, String details) {
        this.code = code;
        this.details = details;
    }

    public VicError(String code, String details, List<FieldError> fieldErrors) {
        this.code = code;
        this.details = details;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldError(objectName, field, message));
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
