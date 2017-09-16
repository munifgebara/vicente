package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicAddress;
import org.hibernate.annotations.Columns;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Audited
public class Endereco extends BaseEntity {

    @Columns(columns = {
            @Column(name = "zipCode"),
            @Column(name = "premisseType"),//tipo logradouro
            @Column(name = "premisse"), //logradouro
            @Column(name = "number"),
            @Column(name = "information"),
            @Column(name = "neighbourhood"), //bairro
            @Column(name = "localization"), //localização
            @Column(name = "state"),
            @Column(name = "country"),
            @Column(name = "latitude"),
            @Column(name = "longitude"),
            @Column(name = "formalCode"),
            @Column(name = "statecode")
    })
    private VicAddress endereco;

    private String descricao;

    public Endereco() {
    }

    public VicAddress getEndereco() {
        return endereco;
    }

    public void setEndereco(VicAddress endereco) {
        this.endereco = endereco;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
