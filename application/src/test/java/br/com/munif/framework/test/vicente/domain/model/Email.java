package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import org.hibernate.annotations.Columns;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN)
public class Email extends BaseEntity {

    @Columns(columns = {
            @Column(name = "description"),
            @Column(name = "social"),
            @Column(name = "invalid"),
            @Column(name = "reason")
    })
    private VicEmail vicEmail;

    public Email() {
    }

    public VicEmail getVicEmail() {
        return vicEmail;
    }

    public void setVicEmail(VicEmail vicEmail) {
        this.vicEmail = vicEmail;
    }
}
