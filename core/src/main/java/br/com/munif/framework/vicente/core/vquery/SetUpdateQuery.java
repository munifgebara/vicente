package br.com.munif.framework.vicente.core.vquery;

public class SetUpdateQuery {
    private String hql;
    private ParamList params;

    public SetUpdateQuery() {
    }

    public SetUpdateQuery(String hql, ParamList params) {
        this.hql = hql;
        this.params = params;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public ParamList getParams() {
        return params;
    }

    public void setParams(ParamList params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return hql;
    }
}
