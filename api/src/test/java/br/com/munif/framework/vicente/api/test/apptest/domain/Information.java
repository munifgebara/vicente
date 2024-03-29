package br.com.munif.framework.vicente.api.test.apptest.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

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

        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
