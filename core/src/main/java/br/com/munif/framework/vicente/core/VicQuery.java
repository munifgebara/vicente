package br.com.munif.framework.vicente.core;

public class VicQuery {

    private String hql = "1=1";

    private int firstResult = 0;

    private int maxResults = Integer.MAX_VALUE;

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
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
    
    

}
