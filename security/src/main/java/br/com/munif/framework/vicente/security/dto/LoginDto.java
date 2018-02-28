/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.dto;

/**
 *
 * @author munif
 */
public class LoginDto {

    public String login;
    public String senha;

    public LoginDto() {
    }

    public LoginDto(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "LoginDto{" + "login=" + login + ", senha=" + senha + '}';
    }

}
