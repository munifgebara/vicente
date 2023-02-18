package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.VicTemporalEntity.VicTemporalBaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author munif
 */
@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN)
public class Salario extends VicTemporalBaseEntity {

    private String nome;

    private BigDecimal valor;

    public Salario() {
    }

    public Salario(String nome, BigDecimal valor) {
        BaseEntity.useSimpleId = true;
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Salario{" + "nome=" + nome + ", valor=" + valor + '}' + super.toString();
    }

}
