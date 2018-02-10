package br.com.munif.framework.vicente.core.vquery;

import java.util.LinkedList;
import java.util.List;

public class Join implements Quereable<Join>, Joinable<Join> {

    /**
     * Tipo de Junção
     */
    private JoinType type;
    /**
     * Tabela
     */
    private String table;

    public Join(String table, JoinType type) {
        this.table = table;
        this.type = type;
    }

    private List<CriteriaJoin> subQuerys = new LinkedList<>();

    @Override
    public Join or(Criteria criteria) {
        this.subQuerys.add(new CriteriaJoin(criteria, CriteriaJoinOperator.OR));
        return this;
    }

    @Override
    public Join and(Criteria criteria) {
        this.subQuerys.add(new CriteriaJoin(criteria, CriteriaJoinOperator.AND));
        return this;
    }

    @Override
    public Join or(Join criteria) {
        return this;
    }

    @Override
    public Join and(Join criteria) {
        return this;
    }

    @Override
    public Join on(Criteria criteria) {
        this.subQuerys.add(new CriteriaJoin(criteria, CriteriaJoinOperator.ON));
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type.getType() + table);
        this.subQuerys.forEach(criteriaJoin -> {
            stringBuilder.append(criteriaJoin.toString());
        });
        return stringBuilder.toString();
    }
}
