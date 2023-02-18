/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.VicTemporalEntity;

/**
 * @author munif
 */
public class ContratoTestEntity extends VicTemporalBaseEntity {

    private String descricao;

    public ContratoTestEntity() {
    }

    public ContratoTestEntity(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
