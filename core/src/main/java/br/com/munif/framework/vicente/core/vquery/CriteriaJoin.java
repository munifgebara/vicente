package br.com.munif.framework.vicente.core.vquery;

/**
 *
 * @author wmfsystem
 */
public class CriteriaJoin {

    private Criteria criteria;

    private CriteriaJoinOperator operator;

    protected CriteriaJoin() {
    }

    public CriteriaJoin(Criteria criteria, CriteriaJoinOperator operator) {
        this.criteria = criteria;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator.getOperator() + criteria.toString();
    }
}
