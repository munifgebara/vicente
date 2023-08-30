/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.domain.dto;

/**
 * @author munif
 */
public class RefreshTokenDto {

    public String token;
    public String refreshToken;

    public RefreshTokenDto() {
    }

    public RefreshTokenDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
