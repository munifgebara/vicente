package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_software")
public class Software extends BaseEntity {

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "software", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnoreProperties({"software"})
    private Set<Operation> operations;

    public Software() {
    }

    public Software(String name, Set<Operation> operations) {
        this.name = name;
        this.operations = operations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    @JsonIgnore
    public Operation getOperation(int index) {
        return new ArrayList<Operation>(this.operations).get(index);
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}