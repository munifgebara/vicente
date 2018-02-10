package br.com.munif.framework.vicente.core.vquery;

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
    IN_ELEMENTS(" in elements "),
    IS(" is "),
    BETWEEN(" between ");
//    NOT_EQUAL(" <> "),
//    NOT_STARTS_WITH(" not like "),
//    NOT_ENDS_WITH(" not like "),
//    NOT_CONTAINS(" = "),
//    NOT_IN(" = "),
//    NOT_IS(" = "),
//    NOT_BETWEEN(" = ");

    public final String comparator;

    ComparisonOperator(String comparator) {
        this.comparator = comparator;
    }

    public String getComparator() {
        return comparator;
    }

    public String getComparation(Object field, Object value) {
        StringBuilder toReturn = new StringBuilder();

        toReturn = toReturn.append(field).append(this.getComparator());

        mount(value, toReturn);

        return toReturn.toString();
    }

    private void mount(Object value, StringBuilder toReturn) {
        if (value instanceof CriteriaField) {
            toReturn.append(((CriteriaField) value).getField());
        } else if (value instanceof String) {
            toReturn.append("'" + prefixString()).append(value).append(posfixString() + "'");
        } else if (value instanceof Object[]) {
            toReturn.append("(");
            Object[] v = (Object[]) value;
            for (int i = 0; i < v.length; i++) {
                mount(v[i], toReturn);
                toReturn.append(",");
            }
            int lastVirgula = toReturn.lastIndexOf(",");
            toReturn.replace(lastVirgula, lastVirgula+1, "");
            toReturn.append(")");
        } else {
            toReturn.append(value);
        }
    }

    private String prefixString() {
        switch (this) {
            case ENDS_WITH:
            case CONTAINS:
                return "%";
            default:
                return "";
        }
    }

    private String posfixString() {
        switch (this) {
            case STARTS_WITH:
            case CONTAINS:
                return "%";
            default:
                return "";
        }
    }
}
