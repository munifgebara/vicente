package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public OperationFilter() {
    }

    public OperationFilter(Operation operation, Profile profile, OperationType operationType) {
        this.operation = operation;
        this.profile = profile;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
