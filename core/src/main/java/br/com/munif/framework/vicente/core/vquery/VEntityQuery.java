package br.com.munif.framework.vicente.core.vquery;

/**
 * @author wmfsystem
 */
public class VEntityQuery extends VQuery {
    private Object entity;

    public VEntityQuery(Object entity) {
        this.entity = entity;
    }

    public VEntityQuery(Object entity, Criteria criteria) {
        this.entity = entity;
        this.setCriteria(criteria);
    }

    public VEntityQuery(Object entity, String alias) {
        this.entity = entity;
        this.setAlias(alias);
    }

    public VEntityQuery(Object entity, String alias, String... fields) {
        this.entity = entity;
        this.setAlias(alias);
        this.setFields(fields);
    }

    public VEntityQuery(Object entity, String alias, Criteria criteria, String... fields) {
        this.entity = entity;
        this.setAlias(alias);
        this.setFields(fields);
        this.setCriteria(criteria);
    }

    public Object getEntity() {
        return entity;
    }

    public Object getEntityName() {
        return (entity instanceof String) ? entity : ((Class) entity).getSimpleName();
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        String where;
        if (null != getLogicalOperator()) {
            where = getLogicalOperator().getOperation(this);
        } else {
            where = LogicalOperator.defaultOperation(this);
        }
        return String.format("(select %s from %s %s where %s)", getFieldsWithAlias(), getEntityName(), getAlias(), where);
    }
}
