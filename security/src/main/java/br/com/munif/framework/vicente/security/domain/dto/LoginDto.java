/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.domain.dto;

/**
 * @author munif
 */
public class LoginDto {

    public String login;
    public String password;
    public String oi;

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

}
