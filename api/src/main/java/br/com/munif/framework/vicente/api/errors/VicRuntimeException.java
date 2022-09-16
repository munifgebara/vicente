package br.com.munif.framework.vicente.api.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public class VicRuntimeException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final List<FieldError> fieldErrors;

    public VicRuntimeException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.fieldErrors = null;
    }

    public VicRuntimeException(HttpStatus httpStatus, List<FieldError> fieldErrors) {
        this.httpStatus = httpStatus;
        this.fieldErrors = fieldErrors;
    }

    public VicRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.fieldErrors = null;
    }

    public VicRuntimeException(String message, HttpStatus httpStatus, List<FieldError> fieldErrors) {
        super(message);
        this.httpStatus = httpStatus;
        this.fieldErrors = fieldErrors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
