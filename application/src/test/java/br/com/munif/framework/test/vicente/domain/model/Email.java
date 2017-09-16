package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import org.hibernate.annotations.Columns;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Audited
public class Email extends BaseEntity{

    @Columns(columns = {
            @Column(name = "description"),
            @Column(name = "social")
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
