package br.com.munif.framework.vicente.security.domain.exceptions;

public class LoginException extends RuntimeException {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
