package br.com.munif.framework.vicente.security.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "WrongPasswordException")
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Wrong password.");
    }
}
