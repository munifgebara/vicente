package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_operation_filter")
public class OperationFilter extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonIgnoreProperties({"filters"})
    private Profile profile;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType operationType = OperationType.ALLOW;
    @Column(name = "requested_count")
    private Integer requestedCount;
    @Column(name = "max_requests")
    private Integer maxRequests;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "operationFilter", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ForwardRequest> forwardRequests;

    public OperationFilter() {
    }

    public OperationFilter(List<ForwardRequest> forwardRequests) {
        this.forwardRequests = forwardRequests;
    }

    public OperationFilter(Operation operation, OperationType operationType) {
        this.operation = operation;
        this.operationType = operationType;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Integer getRequestedCount() {
        return requestedCount;
    }

    public void setRequestedCount(Integer requestedCount) {
        this.requestedCount = requestedCount;
    }

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
    }

    public List<ForwardRequest> getForwardRequests() {
        return forwardRequests;
    }

    public List<ForwardRequest> getEmptyForwardRequests() {
        return Optional.ofNullable(forwardRequests).orElse(new ArrayList<>());
    }

    public void setForwardRequests(List<ForwardRequest> forwardRequests) {
        this.forwardRequests = forwardRequests;
    }
}
