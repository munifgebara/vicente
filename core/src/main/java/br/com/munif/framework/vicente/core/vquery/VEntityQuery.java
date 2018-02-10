package br.com.munif.framework.vicente.core.vquery;

public class VEntityQuery extends VQuery {
    private Object entity;
    private String alias;
    private String[] fields;

    public VEntityQuery(Object entity, String alias) {
        this.entity = entity;
        this.alias = alias;
    }

    public VEntityQuery(Object entity, String alias, String... fields) {
        this.entity = entity;
        this.alias = alias;
        this.fields = fields;
    }

    public VEntityQuery(Object entity, String alias, Criteria criteria, String... fields) {
        this.entity = entity;
        this.alias = alias;
        this.fields = fields;
        this.setCriteria(criteria);
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    public String getAliasWithDot() {
        return (alias != null) ? alias + "." : "";
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String[] getFields() {
        return fields;
    }

    public String getFieldsWithAlias() {
        if (fields != null) {
            String toReturn = "";
            for (int i = 0; i < fields.length; i++) {
                toReturn += getAliasWithDot().concat(fields[i]);
            }
            return toReturn;
        }
        return alias;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return String.format("(select %s from %s %s where %s)", getFieldsWithAlias() , entity, alias, super.toString());
    }
}
