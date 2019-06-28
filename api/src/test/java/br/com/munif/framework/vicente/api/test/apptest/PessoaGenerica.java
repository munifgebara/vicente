package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.domain.tenancyfields.VicTenancyFieldsBaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
public class PessoaGenerica extends VicTenancyFieldsBaseEntity {

    //    @NotNull
    @Column(nullable = false)
    private String nome;

    public PessoaGenerica() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
