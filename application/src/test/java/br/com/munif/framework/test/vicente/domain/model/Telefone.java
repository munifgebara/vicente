package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicPhone;
import org.hibernate.annotations.Columns;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN)
public class Telefone extends BaseEntity {

    @Columns(columns = {
            @Column(name = "description"),
            @Column(name = "type")
    })
    private VicPhone vicPhone;

    public Telefone() {
    }

    public VicPhone getVicPhone() {
        return vicPhone;
    }

    public void setVicPhone(VicPhone vicPhone) {
        this.vicPhone = vicPhone;
    }
}
