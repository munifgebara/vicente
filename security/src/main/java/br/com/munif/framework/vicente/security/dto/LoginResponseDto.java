package br.com.munif.framework.vicente.security.dto;

import br.com.munif.framework.vicente.security.domain.Token;

public class LoginResponseDto {
    public boolean ok;
    public Token token;
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
