package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
public class Information extends BaseEntity {

    @Column(nullable = false)
    private String info;

    public Information() {
    }

    public Information(String info) {
        BaseEntityHelper.setBaseEntityFields(this);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
