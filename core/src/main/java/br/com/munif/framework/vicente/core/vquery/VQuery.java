package br.com.munif.framework.vicente.core.vquery;

import java.util.*;

public class VQuery implements Quereable<VQuery> {

    private LogicalOperator logicalOperator = LogicalOperator.SIMPLE;

    private Criteria criteria;

    private List<Quereable> subQuerys;

    private List<Joinable> joins = new LinkedList<>();

    private Boolean useDistinct = Boolean.FALSE;

    public VQuery() {
        this.logicalOperator = LogicalOperator.SIMPLE;
        this.criteria = new Criteria();
    }

    public VQuery(LogicalOperator logicalOperator, Criteria criteria, List<Quereable> subQuerys) {
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

    public VQuery(LogicalOperator logicalOperator, List<Quereable> subQuerys) {
        this.logicalOperator = logicalOperator;
        this.subQuerys = subQuerys;
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

    public List<Quereable> getSubQuerys() {
        return subQuerys;
    }

    public void setSubQuerys(List<Quereable> subQuerys) {
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

    public void setJoins(List<Joinable> joins) {
        this.joins = joins;
    }

    private void searchUseDistinct(Quereable vQuery, Map<String, Boolean> map) {
        if (!map.get("useDistinct")) {
            map.put("useDistinct", vQuery.getUseDistinct());
        }

        if (vQuery.getSubQuerys() != null) {
            for (Object query : vQuery.getSubQuerys()) {

                if (!map.get("useDistinct")) {
                    map.put("useDistinct", ((Quereable) query).getUseDistinct());
                }
                searchUseDistinct(((Quereable) query), map);
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

    @Override
    public VQuery or(Criteria criteria) {
        VQuery other = new VQuery(criteria);
        if (LogicalOperator.OR.equals(this.logicalOperator)) {
            this.subQuerys = new LinkedList<>(subQuerys);
            this.subQuerys.add(other);
            return this;
        }
        return new VQuery(LogicalOperator.OR, Arrays.asList(new VQuery[]{this, other}));
    }

    @Override
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
        Optional.ofNullable(subQuerys).ifPresent(sub -> sub.forEach(Quereable::addIgnoreCase));
    }

    @Override
    public String toString() {
        if (null != logicalOperator) {
            return logicalOperator.getOperation(this);
        }
        return LogicalOperator.defaultOperation(this);
    }


    public static void main(String[] args) {
        VQuery vQuery = new VQuery(new Criteria("idade", ComparisonOperator.GREATER, 1))
                .and(new VQuery(new Criteria("id", ComparisonOperator.IN, new VEntityQuery("Willian", "wmf",
                        new Criteria("cu", ComparisonOperator.EQUAL, "grande"),
                        "teste"))))
                .or(new VQuery())
                .join(new Join("teste", JoinType.INNER))
                .join(new Join("ola", JoinType.INNER).on(new Criteria("a", ComparisonOperator.GREATER, 2)));

        System.out.println(vQuery);
        System.out.println(vQuery.getJoins());
    }
}
