package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_request_action")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForwardRequest.class, name = "ForwardRequest")
})
public class RequestAction extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private OperationFilter operationFilter;

    public RequestAction() {
    }

    public OperationFilter getOperationFilter() {
        return operationFilter;
    }

    public void setOperationFilter(OperationFilter operationFilter) {
        this.operationFilter = operationFilter;
    }


    @JsonGetter
    public String getType() {
        return this.getClass().getSimpleName();
    }

}