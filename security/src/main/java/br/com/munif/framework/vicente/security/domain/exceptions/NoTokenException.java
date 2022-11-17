package br.com.munif.framework.vicente.security.domain.exceptions;

public class NoTokenException extends RuntimeException {

    public NoTokenException() {
        super("No Token.");
    }

    public NoTokenException(String message) {
        super(message);
    }
}
