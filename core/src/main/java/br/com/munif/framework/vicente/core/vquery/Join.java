package br.com.munif.framework.vicente.core.vquery;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author wmfsystem
 */
public class Join implements Queryable<Join>, Joinable<Join> {

    /**
     * Tipo de Junção
     */
    private JoinType type;
    /**
     * Tabela
     */
    private String table;

    public Join() {
    }



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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.type);
        hash = 19 * hash + Objects.hashCode(this.table);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Join other = (Join) obj;
        if (!Objects.equals(this.table, other.table)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    public JoinType getType() {
        return type;
    }

    public void setType(JoinType type) {
        this.type = type;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public List<CriteriaJoin> getSubQuerys() {
        return subQuerys;
    }

    public void setSubQuerys(List<CriteriaJoin> subQuerys) {
        this.subQuerys = subQuerys;
    }
}
