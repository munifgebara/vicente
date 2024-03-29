package br.com.munif.framework.vicente.domain.tenancyfields;

import br.com.munif.framework.vicente.core.CalendarDateUtil;
import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
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
@VicTenancyPolicy(value = VicTenancyType.ONLY_HIERARCHICAL_TOP_DOWN)
public class VicFieldValue extends BaseEntity {

    @ManyToOne
    private VicField vicField;
    @Column(name = "entity_id")
    private String entityId;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "text_value", length = 2000)
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Object getValue() {
        switch (vicField.getFieldType()) {
            case DATE:
                return dateValue;
            case LOGIC:
                return logicValue;
            case NUMBER:
                return numberValue;
            case SELECT:
            case MULTI_SELECT:
            case TEXT:
                return textValue;
        }
        return null;
    }

    public void setValue(Object value) {
        switch (vicField.getFieldType()) {
            case DATE:
                dateValue = CalendarDateUtil.StringToDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
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
            case SELECT:
            case MULTI_SELECT:
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
