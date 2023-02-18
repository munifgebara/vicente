package br.com.munif.framework.vicente.domain.tenancyfields;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_field_value")
public class VicFieldValue extends BaseEntity {

    @ManyToOne
    private VicField vicField;
    @Column(name = "entity_id")
    private String entityId;
    @Column(name = "text_value")
    private String textValue;
    @Column(name = "number_value")
    private BigDecimal numberValue;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_value")
    private Date dateValue;
    @Column(name = "logic_value")
    private Boolean logicValue;

    public VicFieldValue() {
    }

    public VicFieldValue(VicField vicField) {
        this.vicField = vicField;
    }

    public VicFieldValue(VicField vicField, String entityId, Object value) {
        BaseEntity.useSimpleId = true;
        this.vicField = vicField;
        this.entityId = entityId;
        setValue(value);
    }

    public VicField getVicField() {
        return vicField;
    }

    public void setVicField(VicField vicField) {
        this.vicField = vicField;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public BigDecimal getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(BigDecimal numberValue) {
        this.numberValue = numberValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Boolean getLogicValue() {
        return logicValue;
    }

    public void setLogicValue(Boolean logicValue) {
        this.logicValue = logicValue;
    }

    public Object getValue() {
        switch (vicField.getFieldType()) {
            case DATE:
                return dateValue;
            case LOGIC:
                return logicValue;
            case NUMBER:
                return numberValue;
            case SELECTION:
            case TEXT:
                return textValue;
        }
        return null;
    }

    public void setValue(Object value) {
        switch (vicField.getFieldType()) {
            case DATE:
                dateValue = (Date) value;
                break;
            case LOGIC:
                logicValue = (Boolean) value;
                break;
            case NUMBER:
                if (value instanceof BigDecimal) {
                    numberValue = (BigDecimal) value;
                } else {
                    numberValue = new BigDecimal(value.toString());
                }
                break;
            case SELECTION:
            case TEXT:
                textValue = (String) value;
                break;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
