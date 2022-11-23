package br.com.munif.framework.vicente.security.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "TokenExpiredException")
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token expired.");
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
