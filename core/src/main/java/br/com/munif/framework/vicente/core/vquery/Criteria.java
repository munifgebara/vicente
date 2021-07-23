package br.com.munif.framework.vicente.core.vquery;

import br.com.munif.framework.vicente.core.phonetics.PhoneticBuilder;

import java.util.Map;

/**
 * @author wmfsystem
 */
public class Criteria {

    private Object field;
    private ComparisonOperator comparisonOperator;
    private Object value;
    private Object[] values;
    private String fieldFn;
    private String valueFn;
    private Param param;
    private Boolean phonetic;

    private void onInit() {
        comparisonOperator = ComparisonOperator.EQUAL;
        fieldFn = null;
        valueFn = null;
        phonetic = false;
        if (!(value instanceof CriteriaField))
            param = new Param(null, value != null ? value.getClass() : Object.class, field);
    }

    public Criteria() {
        field = 1;
        value = 1;
        onInit();
    }

    public Criteria(Object field, ComparisonOperator comparisonOperator, Object value) {
        this.field = field;
        this.value = value;
        onInit();
        this.comparisonOperator = comparisonOperator;
    }

    public Object getField() {
        return (fieldFn != null ? String.format(fieldFn, field) : field);
    }

    public void setField(Object field) {
        this.param.setField(String.valueOf(field));
        this.field = field;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public Object getValue() {
        if (this.value instanceof String && this.phonetic) {
            value = PhoneticBuilder.build().translate(String.valueOf(value));
        }
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public String getFieldFn() {
        return fieldFn;
    }

    public void setFieldFn(String fieldFn) {
        this.fieldFn = fieldFn;
    }

    public String getValueFn() {
        return valueFn;
    }

    public void setValueFn(String valueFn) {
        this.valueFn = valueFn;
    }

    private Object getFieldByMap(Object value) {
        Map cast = Map.class.cast(value);
        if (cast.containsKey("field")) {
            value = new CriteriaField(cast.get("field").toString());
        }
        return value;
    }

    public Criteria addIgnoreCase() {
        fieldFn = String.format(fieldFn, "lower(%s)");
        valueFn = String.format(valueFn, "lower(%s)");
        return this;
    }

    public Criteria addPhonetic() {
        this.phonetic = true;
        return this;
    }

    @Override
    public String toString() {
        return comparisonOperator.getComparation(getField(), value instanceof VEntityQuery || value instanceof CriteriaField || ComparisonOperator.NONE.equals(comparisonOperator) ? getValue() : getParam().getKey(), valueFn);
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }
}
