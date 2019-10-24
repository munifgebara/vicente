package br.com.munif.framework.vicente.core.vquery;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wmfsystem
 */
public class VQuery {
    private LogicalOperator logicalOperator = LogicalOperator.SIMPLE;
    private Criteria criteria;
    private List<VQuery> subQuerys = new LinkedList<>();
    private List<Join> joins = new LinkedList<>();
    private Boolean useDistinct = Boolean.FALSE;
    private String alias;
    private String[] fields;

    public VQuery() {
        this.criteria = new Criteria();
    }

    public VQuery(LogicalOperator logicalOperator, Criteria criteria, List<VQuery> subQuerys) {
        this.logicalOperator = logicalOperator;
        this.criteria = criteria;
        this.subQuerys = subQuerys;
    }

    public VQuery(LogicalOperator logicalOperator, Criteria criteria) {
        this.logicalOperator = logicalOperator;
        this.criteria = criteria;
    }

    public VQuery(Criteria criteria) {
        this.criteria = criteria;
    }

    public VQuery(Criteria criteria, String... fields) {
        this.criteria = criteria;
        this.fields = fields;
    }

    public VQuery(LogicalOperator logicalOperator, List<VQuery> subQuerys) {
        this.logicalOperator = logicalOperator;
        this.subQuerys = subQuerys;
        adjustJoins(this);
    }

    public VQuery(LogicalOperator logicalOperator, List<VQuery> subQuerys, String... fields) {
        this.logicalOperator = logicalOperator;
        this.subQuerys = subQuerys;
        this.fields = fields;
        adjustJoins(this);
    }

    private void adjustJoins(VQuery vQuery) {
        if (vQuery.joins != null && vQuery.joins.size() > 0) {
            this.joins.addAll(vQuery.joins);
            vQuery.joins = new LinkedList<>();
            if (vQuery.getSubQuerys() != null) {
                vQuery.getSubQuerys().forEach(sub -> adjustJoins(vQuery));
            }
        }
    }

    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(LogicalOperator logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<VQuery> getSubQuerys() {
        return subQuerys;
    }

    public void setSubQuerys(List<VQuery> subQuerys) {
        this.subQuerys = subQuerys;
    }

    private void mountJoins(VQuery vQuery, StringBuilder builder) {
        vQuery.joins.forEach(builder::append);
        if (vQuery.getSubQuerys() != null) {
            vQuery.getSubQuerys().forEach(sub -> mountJoins((VQuery) sub, builder));
        }
    }

    public String getJoins() {
        StringBuilder joinStr = new StringBuilder();
        mountJoins(this, joinStr);
        return joinStr.toString();
    }

    public String getJoinsWithoutParam() {
        StringBuilder joinStr = new StringBuilder();
        mountJoins(this, joinStr);
        return replaceParams(joinStr.toString());
    }

    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }

    private void searchUseDistinct(VQuery vQuery, Map<String, Boolean> map) {
        if (!map.get("useDistinct")) {
            map.put("useDistinct", vQuery.getUseDistinct());
        }

        if (vQuery.getSubQuerys() != null) {
            for (Object query : vQuery.getSubQuerys()) {

                if (!map.get("useDistinct")) {
                    map.put("useDistinct", ((VQuery) query).getUseDistinct());
                }
                searchUseDistinct(((VQuery) query), map);
            }
        }
    }

    public Boolean useDistinct() {
        Map<String, Boolean> toReturn = new HashMap<>();
        toReturn.put("useDistinct", Boolean.FALSE);
        this.searchUseDistinct(this, toReturn);
        return toReturn.get("useDistinct");
    }

    public Boolean getUseDistinct() {
        return useDistinct;
    }

    public void setUseDistinct(Boolean useDistinct) {
        this.useDistinct = useDistinct;
    }

    public VQuery or(Criteria criteria) {
        VQuery other = new VQuery(criteria);
        if (LogicalOperator.OR.equals(this.logicalOperator)) {
            this.subQuerys = new LinkedList<>(subQuerys);
            this.subQuerys.add(other);
            return this;
        }

        VQuery vQuery = new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{this, other}));

        return vQuery;
    }

    public VQuery and(Criteria criteria) {
        VQuery other = new VQuery(criteria);
        if (LogicalOperator.AND.equals(this.logicalOperator)) {
            this.subQuerys = new LinkedList<>(subQuerys);
            this.subQuerys.add(other);
            return this;
        }
        return new VQuery(LogicalOperator.AND, Arrays.asList(new VQuery[]{this, other}));
    }

    public VQuery and(VQuery other) {
        if (LogicalOperator.AND.equals(this.logicalOperator)) {
            this.subQuerys.add(other);
            return this;
        }
        return new VQuery(LogicalOperator.AND, Arrays.asList(new VQuery[]{this, other}));
    }

    public VQuery or(VQuery other) {
        if (LogicalOperator.OR.equals(this.logicalOperator)) {
            this.subQuerys = new LinkedList<>(subQuerys);
            this.subQuerys.add(other);
            return this;
        }
        return new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{this, other}));
    }

    public VQuery join(Join join) {
        this.joins.add(join);
        return this;
    }

    public void addIgnoreCase() {
        Optional.ofNullable(criteria).ifPresent(Criteria::addIgnoreCase);
        Optional.ofNullable(subQuerys).ifPresent(sub -> sub.forEach(VQuery::addIgnoreCase));
    }

    public String getFieldsWithAlias() {
        if (getFields() != null) {
            StringBuilder toReturn = new StringBuilder();
            for (int i = 0; i < getFields().length; i++) {
                toReturn.append(getAliasWithDot().concat(getFields()[i]).concat(" as ").concat(getFields()[i]));
            }
            return toReturn.toString();
        }
        return getAlias();
    }

    @Override
    public String toString() {
        if (this.criteria != null && this.criteria.getField().equals(1) && this.getSubQuerys().size() > 0) {
            this.criteria = null;
        }
        if (null != logicalOperator) {
            return logicalOperator.getOperation(this);
        }
        return LogicalOperator.defaultOperation(this);
    }

    public String toStringWithoutParams() {
        String s = this.toString();
        s = replaceParams(s);
        return s;
    }

    private String replaceParams(String s) {
        ParamList params = getParams();
        for (Param entry : params) {
            String val = String.valueOf(entry.getValue());
            s = s.replace(entry.getKey(), val);
        }
        return s;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getAlias() {
        return alias == null ? "obj" : alias;
    }

    public String getAliasWithDot() {
        return (alias != null) ? alias + "." : "";
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ParamList getParams() {
        ParamList params = new ParamList();
        getParams(this, params);
        return params;
    }

    public void getParams(VQuery vQuery, ParamList params) {
        if (vQuery != null) {
            if (vQuery.getCriteria() != null) {
                StringBuilder toReturn = new StringBuilder();
                Object value = vQuery.getCriteria().getValue();
                if (value instanceof VEntityQuery) {
                    getParams(((VEntityQuery) value), params);
                } else {
                    ComparisonOperator.mount(value, toReturn, vQuery.getCriteria().getComparisonOperator());
                    params.add(vQuery.getCriteria().getParam().setBuilderValue(toReturn.toString()));
                }
            }
            for (VQuery subQuery : vQuery.getSubQuerys()) {
                getParams(subQuery, params);
            }
            for (Join join : vQuery.joins) {
                for (CriteriaJoin subQuery : join.getSubQuerys()) {
                    params.addAll(subQuery.getParams());
                }
            }
        }
    }
}
