package br.com.munif.framework.vicente.domain.tenancyfields;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author munif
 */
@Entity
@Audited
@Table(name = "vic_field")
public class VicField extends BaseEntity {

    @Column(name = "clazz")
    private String clazz;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "field_type")
    private VicFieldType fieldType;
    @Column(name = "validation_description")
    private String validationDescription;
    @Column(name = "validation_script")
    private String validationScript;
    @Column(name = "default_value_script")
    private String defaultValueScript;
    @Column(name = "options")
    private String options;
    @Column(name = "option_value_field")
    private String optionValueField;
    @Column(name = "option_label_field")
    private String optionLabelField;
    @Column(name = "options_collection")
    private String optionsCollection;
    @Column(name = "visualization_order")
    private Double visualizationOrder;
    @Column(name = "field_group")
    private String fieldGroup;
    @Column(name = "translateKey")
    private String translateKey;

    public VicField() {
    }

    public VicField(String clazz, String name, String description, VicFieldType fieldType, String validationDescription, String validationScript, String defaultValueScript, String options, String optionValueField, String optionLabelField, String optionsCollection, Double visualizationOrder, String fieldGroup, String translateKey) {
        this.clazz = clazz;
        this.name = name;
        this.description = description;
        this.fieldType = fieldType;
        this.validationDescription = validationDescription;
        this.validationScript = validationScript;
        this.defaultValueScript = defaultValueScript;
        this.options = options;
        this.optionValueField = optionValueField;
        this.optionLabelField = optionLabelField;
        this.optionsCollection = optionsCollection;
        this.visualizationOrder = visualizationOrder;
        this.fieldGroup = fieldGroup;
        this.translateKey = translateKey;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VicFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(VicFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getValidationDescription() {
        return validationDescription;
    }

    public void setValidationDescription(String validationDescription) {
        this.validationDescription = validationDescription;
    }

    public String getValidationScript() {
        return validationScript;
    }

    public void setValidationScript(String validationScript) {
        this.validationScript = validationScript;
    }

    public String getDefaultValueScript() {
        return defaultValueScript;
    }

    public void setDefaultValueScript(String defaultValueScript) {
        this.defaultValueScript = defaultValueScript;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptionValueField() {
        return optionValueField;
    }

    public void setOptionValueField(String optionValueField) {
        this.optionValueField = optionValueField;
    }

    public String getOptionLabelField() {
        return optionLabelField;
    }

    public void setOptionLabelField(String optionLabelField) {
        this.optionLabelField = optionLabelField;
    }

    public String getOptionsCollection() {
        return optionsCollection;
    }

    public void setOptionsCollection(String optionsCollection) {
        this.optionsCollection = optionsCollection;
    }

    public Double getVisualizationOrder() {
        return visualizationOrder;
    }

    public void setVisualizationOrder(Double visualizationOrder) {
        this.visualizationOrder = visualizationOrder;
    }

    public String getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(String fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public String getTranslateKey() {
        return translateKey;
    }

    public void setTranslateKey(String translateKey) {
        this.translateKey = translateKey;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.clazz);
        hash = 19 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VicField other = (VicField) obj;
        if (!Objects.equals(this.clazz, other.clazz)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " VicField{" + "clazz=" + clazz + ", name=" + name + '}';
    }

}
