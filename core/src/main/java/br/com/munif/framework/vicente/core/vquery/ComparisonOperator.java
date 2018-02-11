package br.com.munif.framework.vicente.core.vquery;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum ComparisonOperator {
    EQUAL(" = "),
    LOWER(" < "),
    GREATER(" > "),
    GREATER_EQUAL(" >= "),
    LOWER_EQUAL(" <= "),
    STARTS_WITH(" like "),
    ENDS_WITH(" like "),
    CONTAINS(" like "),
    IN(" in "),
    IN_ELEMENTS(" in elements"),
    IS(" is "),
    BETWEEN(" between "),
    NOT_EQUAL(" <> "),
    NOT_STARTS_WITH(" not like "),
    NOT_ENDS_WITH(" not like "),
    NOT_CONTAINS(" not like "),
    NOT_IN(" not in "),
    NOT_IS(" not is "),
    NOT_BETWEEN(" not between ");

    public final String comparator;

    ComparisonOperator(String comparator) {
        this.comparator = comparator;
    }

    public String getComparator() {
        return comparator;
    }

    public String getComparation(Object field, Object value) {
        StringBuilder toReturn = new StringBuilder();

        String startsValue = (ComparisonOperator.IN_ELEMENTS.equals(this)) ? "(" : "";
        String endsValue = (ComparisonOperator.IN_ELEMENTS.equals(this)) ? ")" : "";
        if (ComparisonOperator.IN_ELEMENTS.equals(this)) {
            mount(field, toReturn);
            toReturn.append(this.getComparator()).append(startsValue);
        } else {
            toReturn = toReturn.append(field).append(this.getComparator()).append(startsValue);
        }

        mount(value, toReturn);

        return toReturn.toString().concat(endsValue);
    }

    private void mount(Object value, StringBuilder toReturn) {
        if (value instanceof CriteriaField) {
            toReturn.append(((CriteriaField) value).getField());
        } else if (value instanceof String) {
            toReturn.append("'" + prefixString()).append(value).append(posfixString() + "'");
        } else if (value instanceof Date) {
            toReturn.append("'").append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value)).append("'");
        } else if (value instanceof Object[]) {
            if (this.equals(ComparisonOperator.BETWEEN) || this.equals(ComparisonOperator.NOT_BETWEEN)) {
                mount(((Object[]) value)[0], toReturn);
                toReturn.append(" and ");
                mount(((Object[]) value)[1], toReturn);
            } else {
                toReturn.append("(");
                Object[] v = (Object[]) value;
                for (int i = 0; i < v.length; i++) {
                    mount(v[i], toReturn);
                    toReturn.append(",");
                }
                int lastVirgula = toReturn.lastIndexOf(",");
                toReturn.replace(lastVirgula, lastVirgula + 1, "");
                toReturn.append(")");
            }
        } else {
            toReturn.append(value);
        }
    }

    private String prefixString() {
        switch (this) {
            case ENDS_WITH:
            case NOT_ENDS_WITH:
            case CONTAINS:
            case NOT_CONTAINS:
                return "%";
            default:
                return "";
        }
    }

    private String posfixString() {
        switch (this) {
            case STARTS_WITH:
            case NOT_STARTS_WITH:
            case CONTAINS:
            case NOT_CONTAINS:
                return "%";
            default:
                return "";
        }
    }
}
