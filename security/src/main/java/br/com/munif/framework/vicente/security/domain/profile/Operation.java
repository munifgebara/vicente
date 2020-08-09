package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_operation")
public class Operation extends BaseEntity {

    @Column(name = "key")
    private String key;
    @ManyToOne
    @JoinColumn(name = "software_id")
    @JsonIgnore
    private Software software;
    @Column(name = "max_requests")
    private Integer maxRequests;

    public Operation() {
    }

    public Operation(String key) {
        this.key = key;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
    }
}
