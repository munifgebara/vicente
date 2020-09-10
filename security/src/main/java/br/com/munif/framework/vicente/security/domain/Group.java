package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_group", indexes = {
        @Index(name = "idx_vic_group_oi", columnList = "oi"),
        @Index(name = "idx_vic_group_ui", columnList = "ui"),
        @Index(name = "idx_vic_group_gi", columnList = "gi"),
        @Index(name = "idx_vic_group_rights", columnList = "rights")
})
public class Group extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;

    public Group() {
    }

    public Group(String name, String code) {
        this.name = name;
        this.code = code;
    }

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
