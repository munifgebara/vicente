package br.com.munif.framework.vicente.security.domain.profile;

import br.com.munif.framework.vicente.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_request_action", indexes = {
        @Index(name = "idx_vic_request_action_oi", columnList = "oi"),
        @Index(name = "idx_vic_request_action_ui", columnList = "ui"),
        @Index(name = "idx_vic_request_action_gi", columnList = "gi"),
        @Index(name = "idx_vic_request_action_rights", columnList = "rights"),
        @Index(name = "idx_vic_request_action_rights", columnList = "operation_filter_id")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForwardRequest.class, name = "ForwardRequest")
})
public class RequestAction extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "operation_filter_id")
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