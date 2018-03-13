package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.tenancyfields.VicTenancyFieldsBaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicAddress;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import br.com.munif.framework.vicente.domain.typings.VicPhone;
import org.hibernate.annotations.Columns;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author munif
 */
@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.HIERARCHICAL_TOP_DOWN)
public class Funcionario extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
