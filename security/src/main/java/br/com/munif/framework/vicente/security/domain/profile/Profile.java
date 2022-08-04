package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.core.Sets;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_profile", indexes = {
        @Index(name = "idx_vic_profile_oi", columnList = "oi"),
        @Index(name = "idx_vic_profile_ui", columnList = "ui"),
        @Index(name = "idx_vic_profile_gi", columnList = "gi"),
        @Index(name = "idx_vic_profile_rights", columnList = "rights")
})
public class Profile extends BaseEntity {

    @Column(name = "name")
    private String name;
    @ManyToMany
    private Set<User> users;
    @OneToMany(mappedBy = "profile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"profile"})
    private Set<OperationFilter> filters;

    public Profile() {
    }

    public Profile(String name, User user, Set<OperationFilter> filters) {
        this.name = name;
        this.users = Sets.newHashSet(user);
        this.filters = filters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OperationFilter> getFilters() {
        return filters;
    }

    public void setFilters(Set<OperationFilter> filters) {
        this.filters = filters;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
