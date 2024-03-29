package br.com.munif.framework.vicente.api.errors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author munif
 * View Model for sending a parameterized error message.
 */
public class ParameterizedError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final Map<String, String> paramMap;

    public ParameterizedError(String message, Map<String, String> paramMap) {
        this.message = message;
        this.paramMap = paramMap;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getParams() {
        return paramMap;
    }

}
