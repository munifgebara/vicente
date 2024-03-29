package br.com.munif.framework.vicente.api.test.apptest.domain;

import br.com.munif.framework.vicente.api.hateoas.HateosBaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author munif
 */
@Entity
@Audited
public class Book extends HateosBaseEntity {

    @Column(nullable = false)
    private String name;

    public Book() {
    }

    public Book(String name) {
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
