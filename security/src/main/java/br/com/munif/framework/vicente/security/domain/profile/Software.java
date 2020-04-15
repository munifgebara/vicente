package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_software")
public class Software extends BaseEntity {

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "software")
    @JsonIgnoreProperties({"software"})
    private List<Operation> operations;

    public Software() {
    }

    public Software(String name, List<Operation> operations) {
        this.name = name;
        this.operations = operations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}