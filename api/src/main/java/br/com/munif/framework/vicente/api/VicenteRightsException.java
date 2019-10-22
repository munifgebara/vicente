package br.com.munif.framework.vicente.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author munif
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class VicenteRightsException extends RuntimeException{
    public VicenteRightsException(String message) {
        super(message);
    }
}
