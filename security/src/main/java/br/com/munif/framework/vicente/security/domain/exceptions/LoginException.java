package br.com.munif.framework.vicente.security.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class LoginException extends RuntimeException {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
