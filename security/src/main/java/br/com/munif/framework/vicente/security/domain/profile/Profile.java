package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_profile")
public class Profile extends BaseEntity {

    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "profile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"profile"})
    private List<OperationFilter> filters;

    public Profile() {
    }

    public Profile(String name, User user, List<OperationFilter> filters) {
        this.name = name;
        this.user = user;
        this.filters = filters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OperationFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<OperationFilter> filters) {
        this.filters = filters;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
