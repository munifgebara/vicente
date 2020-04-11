package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author munif
 */
@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.COMMUM)
@Table(name = "vic_user")
public class User extends BaseEntity {

    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Group> groups;
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Group getGroupByIndex(int index) {
        return (Group) getGroups().toArray()[index];
    }

    public Group getFirstGroup() {
        return getGroups().stream().findFirst().orElse(null);
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void assignGroups(Collection<Group> groups) {
        if (this.groups == null) this.groups = new HashSet<>();
        this.groups.addAll(groups);
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String stringGroups() {
        if (this.getGroups() == null) {
            return null;
        }
        String s = "";
        for (Group g : this.getGroups()) {
            s += g.getCode() + ",";
        }

        return s;

    }

    public String stringOrganization() {
        if (organization == null) {
            return null;
        }
        return getOrganization().getCode();
    }

}
