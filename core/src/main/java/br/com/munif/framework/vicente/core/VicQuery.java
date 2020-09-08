package br.com.munif.framework.vicente.core;

import br.com.munif.framework.vicente.core.vquery.VQuery;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author munif
 */
public class VicQuery {

    public static final int DEFAULT_QUERY_SIZE = 1000000000;
    public static final String DEFAULT_QUERY = "1=1";
    private static final String DEFAULT_ORDER_BY = "id";
    private String hql = DEFAULT_QUERY;
    private VQuery query;
    @JsonIgnore
    private String entity;
    private int firstResult = 0;
    private int maxResults = -1;
    private String orderBy = DEFAULT_ORDER_BY;
    private String sortDir = "asc";

    public VicQuery() {
    }

    public VicQuery(String hql, int firstResult, int maxResults, String orderBy) {
        this.hql = hql;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.orderBy = orderBy;
    }

    public VicQuery(VQuery query, int firstResult, int maxResults, String orderBy) {
        this.query = query;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.orderBy = orderBy;
    }

    public VicQuery(VQuery query, int maxResults) {
        this.query = query;
        this.maxResults = maxResults;
    }

    public VicQuery(VQuery query) {
        this.query = query;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public VQuery getQuery() {
        return query;
    }

    public void setQuery(VQuery query) {
        this.query = query;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    @Override
    public String toString() {
        return "VicQuery{" + "hql=" + hql + ", firstResult=" + firstResult + ", maxResults=" + maxResults + ", orderBy=" + orderBy + '}';
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
