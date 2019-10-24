/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.dto;

/**
 * @author munif
 */
public class LoginDto {

    public String login;
    public String password;

    public LoginDto() {
    }

    public LoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDto{" + "login=" + login + ", senha=" + password + '}';
    }

}
