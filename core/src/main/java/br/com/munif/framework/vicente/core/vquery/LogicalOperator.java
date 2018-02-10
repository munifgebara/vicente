package br.com.munif.framework.vicente.core.vquery;

public enum LogicalOperator {
    SIMPLE, NOT, OR, AND;

    public String getOperation(VQuery vQuery) {
        switch (this) {
            case SIMPLE:
                return "(" + vQuery.getCriteria().toString() + ")";
            case NOT:
                return "(!" + vQuery.getCriteria().toString() + ")";
            case OR:
            case AND:
                if (vQuery.getSubQuerys() == null || vQuery.getSubQuerys().isEmpty()) {
                    return "1 = 1";
                }
                StringBuilder toReturn = new StringBuilder("(" + vQuery.getSubQuerys().get(0));
                for (int i = 1; i < vQuery.getSubQuerys().size(); i++) {
                    toReturn.append(" ")
                            .append(vQuery.getLogicalOperator().toString())
                            .append(" ")
                            .append(vQuery.getSubQuerys().get(i).toString());
                }
                toReturn.append(")");
                return toReturn.toString();
            default:
                break;
        }
        return LogicalOperator.defaultOperation(vQuery);
    }

    public static String defaultOperation(VQuery vQuery) {
        return "(" + vQuery.getCriteria() + ")";
    }
}
