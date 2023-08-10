/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.domain.dto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author munif
 */
public class LoginDto {

    private String login;
    private String password;
    private String oi;
    private Boolean enc;


    public LoginDto() {
    }

    public LoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginDto(String login, String password, String oi) {
        this.login = login;
        this.password = password;
        this.oi = oi;
    }

    @Override
    public String toString() {
        return "LoginDto{" + "login=" + login + ", senha=" + password + '}';
    }

    public String getLogin() {
        if (getEnc())
            return getDecoded(login);
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private static String getEncoded(String txt) {
        return Base64.getEncoder().encodeToString(txt.getBytes(StandardCharsets.UTF_8));
    }
    private static String getDecoded(String txt) {
        return new String(Base64.getDecoder().decode(txt), StandardCharsets.UTF_8);
    }

    public String getPassword() {
        if (getEnc())
            return getDecoded(password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOi() {
        return oi;
    }

    public void setOi(String oi) {
        this.oi = oi;
    }

    public Boolean getEnc() {
        return enc != null && enc;
    }

    public void setEnc(Boolean enc) {
        this.enc = enc;
    }
}
