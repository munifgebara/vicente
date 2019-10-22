package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author munif
 */
@Entity
@Audited
public class Group extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
