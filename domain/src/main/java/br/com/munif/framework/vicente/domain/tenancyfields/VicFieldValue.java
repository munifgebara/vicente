package br.com.munif.framework.vicente.domain.tenancyfields;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import org.hibernate.envers.Audited;

/**
 *
 * @author munif
 */
@Entity
@Audited
public class VicFieldValue extends BaseEntity {

    @ManyToOne
    private VicField vicField;

    private String entityId;

    private String textValue;
    private BigDecimal numberValue;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateValue;
    private Boolean logicValue;

    public VicFieldValue() {
    }

    public VicFieldValue(VicField vicField) {
        this.vicField = vicField;
    }
    
    public VicFieldValue(VicField vicField, String entityId, Object value) {
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

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
