package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
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

    @Column(name = "api")
    private String api;
    @Column(name = "method")
    private String method;
    @ManyToOne
    @JoinColumn(name = "software_id")
    @JsonIgnoreProperties({"operations"})
    private Software software;
    @Column(name = "max_requests")
    private Integer maxRequests;

    public Operation() {
    }

    public Operation(String api, String method) {
        this.api = api;
        this.method = method;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
    }
}
