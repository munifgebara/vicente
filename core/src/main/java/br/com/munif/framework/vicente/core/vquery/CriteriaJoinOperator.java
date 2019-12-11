package br.com.munif.framework.vicente.core.vquery;

/**
 *
 * @author wmfsystem
 */
public enum CriteriaJoinOperator {
    ON(" on "),
    AND(" and "),
    OR(" or ");

    private String operator;

    CriteriaJoinOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
