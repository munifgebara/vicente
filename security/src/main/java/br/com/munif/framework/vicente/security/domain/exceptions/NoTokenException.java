package br.com.munif.framework.vicente.security.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "NoTokenException")
public class NoTokenException extends RuntimeException {

    public NoTokenException() {
        super("No Token.");
    }

    public NoTokenException(String message) {
        super(message);
    }
}
