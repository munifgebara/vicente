package br.com.munif.framework.vicente.core.vquery;

import java.util.HashMap;
import java.util.Map;

/**
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

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public CriteriaJoinOperator getOperator() {
        return operator;
    }

    public void setOperator(CriteriaJoinOperator operator) {
        this.operator = operator;
    }

    public Map<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        getParams(this, params);
        return params;
    }

    public void getParams(CriteriaJoin vQuery, HashMap<String, Object> params) {
        if (vQuery != null) {
            if (vQuery.getCriteria() != null) {
                StringBuilder toReturn = new StringBuilder();
                Object value = vQuery.getCriteria().getValue();
                if (value instanceof VEntityQuery) {
                    getParams(((CriteriaJoin) value), params);
                } else {
                    ComparisonOperator.mount(value, toReturn, vQuery.getCriteria().getComparisonOperator());
                }
                params.put(vQuery.getCriteria().getParam(), toReturn.toString());
            }
        }
    }
}
