package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Grupo extends BaseEntity {

    private String nome;

    private String codigo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
