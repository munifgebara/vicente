package br.com.munif.framework.vicente.api.errors;

import br.com.munif.framework.vicente.api.VicenteCreateWithExistingIdException;
import br.com.munif.framework.vicente.api.VicenteErrorOnRequestException;
import br.com.munif.framework.vicente.api.VicenteNotFoundException;
import br.com.munif.framework.vicente.api.VicenteRightsException;
import org.hibernate.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author munif
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public VicError processConcurrencyError(ConcurrencyFailureException ex) {
        return new VicError(ErrorConstants.ERR_CONCURRENCY_FAILURE, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public VicError processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        VicError dto = new VicError(ErrorConstants.ERR_VALIDATION, ex.getMessage());
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }
        return dto;
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedError processParameterizedValidationError(CustomParameterizedException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VicError processMissingServletRequestPartException(MissingServletRequestPartException e) {
        return new VicError("error.http." + HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VicError constraintViolationException(org.springframework.dao.DataIntegrityViolationException e) {
        return new VicError(ErrorConstants.ERR_DATA_INTEGRITY_VIOLATION, e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VicError processMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new VicError("error.http." + HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public VicError processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new VicError(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(VicenteRightsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public VicError vicenteRightsException(VicenteRightsException exception) {
        return new VicError(ErrorConstants.ERR_NOT_ALLOWED, exception.getMessage());
    }

    @ExceptionHandler(VicenteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public VicError vicenteNotFoundException(VicenteNotFoundException exception) {
        return new VicError(ErrorConstants.ERR_NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(VicenteCreateWithExistingIdException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public VicError vicenteCreateWithExistingIdException(VicenteCreateWithExistingIdException exception) {
        return new VicError(ErrorConstants.ERR_CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(VicenteErrorOnRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VicError vicenteCreateWithExistingIdException(VicenteErrorOnRequestException exception) {
        return new VicError(ErrorConstants.ERR_BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(QueryException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VicError queryException(QueryException exception) {
        return new VicError(ErrorConstants.ERR_DATA_INTEGRITY_VIOLATION, exception.getMessage());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VicError invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException exception) {
        return new VicError(ErrorConstants.ERR_DATA_INTEGRITY_VIOLATION, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<VicError> processException(Exception ex) {
        log.debug("--->" + ex.getClass() + " " + ex.getMessage() + " " + ex.getCause());
        ex.printStackTrace();
        if (log.isDebugEnabled()) {
            log.debug("An unexpected error occurred: {}", ex.getMessage(), ex);
        } else {
            log.error("An unexpected error occurred: {}", ex.getMessage());
        }
        BodyBuilder builder;
        VicError vicError;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            vicError = new VicError(ex.getClass().getSimpleName(), ex.getMessage() != null && !ex.getMessage().isEmpty() ? ex.getMessage() : responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            vicError = new VicError(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        return builder.body(vicError);
    }

    @ExceptionHandler(VicRuntimeException.class)
    @ResponseBody
    public VicError vicRuntimeException(HttpServletResponse response, VicRuntimeException ex) {
        log.error("Error on operation", ex);
        response.setStatus(ex.getHttpStatus().value());
        VicError errorResource = new VicError(ex.getClass().getSimpleName(), ex.getMessage());
        errorResource.setFieldErrors(ex.getFieldErrors());
        return errorResource;
    }
}
