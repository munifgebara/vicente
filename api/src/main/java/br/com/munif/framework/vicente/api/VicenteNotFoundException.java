package br.com.munif.framework.vicente.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author munif
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VicenteNotFoundException extends RuntimeException{

    public VicenteNotFoundException(String message) {
        super(message);
    }
    
}
