package br.com.munif.framework.vicente.security.domain.dto;

public class LoginResponseAppDto {
    public String token;
    public String message;
    public int code;

    public LoginResponseAppDto() {
    }

    public LoginResponseAppDto(String token) {
        this.token = token;
    }
}
