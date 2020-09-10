package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_operation", indexes = {
        @Index(name = "idx_vic_operation_oi", columnList = "oi"),
        @Index(name = "idx_vic_operation_ui", columnList = "ui"),
        @Index(name = "idx_vic_operation_gi", columnList = "gi"),
        @Index(name = "idx_vic_operation_rights", columnList = "rights"),
        @Index(name = "idx_vic_operation_software", columnList = "software_id")
})
public class Operation extends BaseEntity {

    @Column(name = "key")
    private String key;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "software_id")
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
