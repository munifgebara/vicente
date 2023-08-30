package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

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
    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"profile"})
    private Set<OperationFilter> filters;

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

    public Set<OperationFilter> getFilters() {
        return filters;
    }

    public void setFilters(Set<OperationFilter> filters) {
        this.filters = filters;
    }
}
