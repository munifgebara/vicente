package br.com.munif.framework.test.vicente.application.model;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
/**
 *
 * @author munif
 */
@Entity
@Audited
public class Pessoa extends BaseEntity {

    @NotNull
    @Column( nullable = false)
    private String nome;
    private String apelido;
    private String documento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date nascimento;

    public Pessoa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }


}
