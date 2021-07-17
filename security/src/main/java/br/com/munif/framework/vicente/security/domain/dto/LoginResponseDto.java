package br.com.munif.framework.vicente.security.domain.dto;

import br.com.munif.framework.vicente.security.domain.Token;

import java.util.HashMap;
import java.util.Map;

public class LoginResponseDto {
    public boolean ok;
    public Token token;
    public Map<String, Object> additionalInfo = new HashMap<>();
    public String message;
    public int code;

    public LoginResponseDto() {
    }

    public LoginResponseDto(boolean ok, Token token, String message, int code) {
        this.ok = ok;
        this.token = token;
        this.message = message;
        this.code = code;
    }
}
