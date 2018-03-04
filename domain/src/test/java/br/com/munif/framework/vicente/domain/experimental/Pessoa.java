package br.com.munif.framework.vicente.domain.experimental;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.io.Serializable;

public class Pessoa extends BaseEntity implements Serializable {

    private String nome;

    private Pessoa irmao;

    private Familia familia;

    public Pessoa() {
    }

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pessoa getIrmao() {
        return irmao;
    }

    public void setIrmao(Pessoa irmao) {
        this.irmao = irmao;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

}
