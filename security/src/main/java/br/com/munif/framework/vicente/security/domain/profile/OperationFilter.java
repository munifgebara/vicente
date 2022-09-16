package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_operation_filter", indexes = {
        @Index(name = "idx_vic_operation_filter_oi", columnList = "oi"),
        @Index(name = "idx_vic_operation_filter_ui", columnList = "ui"),
        @Index(name = "idx_vic_operation_filter_gi", columnList = "gi"),
        @Index(name = "idx_vic_operation_filter_rights", columnList = "rights"),
        @Index(name = "idx_vic_operation_filter_operation", columnList = "operation_id"),
        @Index(name = "idx_vic_operation_filter_profile", columnList = "profile_id")
})
public class OperationFilter extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private Group group;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType operationType = OperationType.ALLOW;
    @Column(name = "requested_count")
    private Integer requestedCount;
    @Column(name = "max_requests")
    private Integer maxRequests;
    @OneToMany(mappedBy = "operationFilter", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<RequestAction> actions;

    public OperationFilter() {
    }

    public OperationFilter(List<RequestAction> actions) {
        this.actions = actions;
    }

    public OperationFilter(Operation operation, OperationType operationType) {
        this.operation = operation;
        this.operationType = operationType;
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
        return requestedCount != null ? requestedCount : 0;
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

    public List<RequestAction> getActions() {
        return actions;
    }

    public void setActions(List<RequestAction> actions) {
        this.actions = actions;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
