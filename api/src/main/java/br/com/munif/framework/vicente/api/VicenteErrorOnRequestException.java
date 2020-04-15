/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author munif
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VicenteErrorOnRequestException extends RuntimeException {
    public VicenteErrorOnRequestException(String string) {
        super(string);
    }
}
