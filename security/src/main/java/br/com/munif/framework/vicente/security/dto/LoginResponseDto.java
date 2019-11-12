package br.com.munif.framework.vicente.security.dto;

import br.com.munif.framework.vicente.security.domain.Token;

public class LoginResponseDto {
    public boolean ok;
    public Token token;
    public String message;
    public int code;
}
