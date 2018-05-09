package br.com.munif.framework.test.vicente.domain.model.smartsearch;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
public class GrupoClientes extends BaseEntity {

    private String nomeGrupo;

    public GrupoClientes() {
    }

    public GrupoClientes(String nomeGrupo) {
        BaseEntity.useSimpleId=true;
        this.nomeGrupo = nomeGrupo;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

}
