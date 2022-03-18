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
public class Validator extends BaseEntity {


    @Column(nullable = false)
    private String name;

    public Validator() {
    }

    public Validator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + " " + name;
    }


}
