/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class VicRepositoryImpl<T> extends SimpleJpaRepository<T, Serializable> implements VicRepository<T> {

    private final EntityManager entityManager;

    public VicRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public String geTenancyHQL(boolean publics) {
        Class<T> domainClass = this.getDomainClass();
        boolean assignableFrom2 = BaseEntity.class.isAssignableFrom(domainClass);
        if (!assignableFrom2) {
            return "(1=1)";
        }
        VicTenancyType vtt = getPolicy(domainClass);
        StringBuilder sb = new StringBuilder("(\n");
        sb.append("   (obj.ui=:ui and mod(obj.rights/64,8)/4>=1) \n");

        if (vtt.equals(VicTenancyType.GROUPS) || vtt.equals(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN) || vtt.equals(VicTenancyType.GROUPS_AND_ORGANIZATIONAL)) {
            sb.append("or (:gi like concat('%',obj.gi,',%') and mod(obj.rights/8,8)/4>=1) \n");
        }

        if (vtt.equals(VicTenancyType.HIERARCHICAL_TOP_DOWN) || vtt.equals(VicTenancyType.ORGANIZATIONAL) || vtt.equals(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN) || vtt.equals(VicTenancyType.GROUPS_AND_ORGANIZATIONAL)) {
            sb.append("or (obj.oi like :oi)\n");
        }
        if (publics) {
            sb.append("or (1=1    and    mod(obj.rights  ,8)/4>=1)\n");
        }

        sb.append(
                ")");
        return sb.toString();
    }

    public VicTenancyType getPolicy(Class<T> domainClass) {
        VicTenancyPolicy vtp = domainClass.getAnnotation(VicTenancyPolicy.class);
        VicTenancyType vtt = vtp == null ? VicTenancyType.GROUPS : vtp.value();
        return vtt;
    }

    public void setTenancyParameters(Query query) {
        Set<Parameter<?>> parameters = query.getParameters();
        for (Parameter p : parameters) {
            if ("ui".equals(p.getName())) {
                query.setParameter("ui", VicThreadScope.ui.get());
            }
            if ("gi".equals(p.getName())) {
                query.setParameter("gi", VicThreadScope.gi.get() + ",");
            }
            if ("oi".equals(p.getName())) {
                VicTenancyType vtt = getPolicy(getDomainClass());
                String oi = VicThreadScope.oi.get();
                if (oi==null){
                    oi=".";
                }
                if (vtt.equals(VicTenancyType.ORGANIZATIONAL) || vtt.equals(VicTenancyType.GROUPS_AND_ORGANIZATIONAL)) {
                    query.setParameter("oi", oi.substring(0,oi.indexOf('.')+1) + "%");
                } else {
                    query.setParameter("oi", oi + "%");
                }
            }

        }
    }

    public List<T> findAllNoTenancy() {
        return super.findAll();
    }

    @Override
    public List<T> findAll() {
        String hql = "FROM " + getDomainClass().getSimpleName() + " obj where \n" + geTenancyHQL(true);
        Query query = entityManager.createQuery(hql);
        setTenancyParameters(query);
        return query.getResultList();
    }

    public List<T> findAllNoPublic() {
        String hql = "FROM " + getDomainClass().getSimpleName() + " obj where \n" + geTenancyHQL(false);
        Query query = entityManager.createQuery(hql);
        setTenancyParameters(query);
        return query.getResultList();
    }

    @Override
    public List<T> findByHql(VicQuery vicQuery) {
        if (vicQuery.getMaxResults() == -1) {
            vicQuery.setMaxResults(VicQuery.DEFAULT_QUERY_SIZE);
        }

        String clause = (vicQuery.getHql() != null ? vicQuery.getHql() : "1=1")
                .concat(" and ")
                .concat((vicQuery.getQuery() != null ? vicQuery.getQuery().toString(): "1=1"));

        String joins = (vicQuery.getQuery() != null) ? vicQuery.getQuery().getJoins() : "";

        String hql = "select obj FROM " + getDomainClass().getSimpleName() + " obj "+ joins +" where \n"
                + "(" + clause + ") and "
                + "("
                + geTenancyHQL(true)
                + ") "
                + " ORDER BY obj." + vicQuery.getOrderBy() + " , obj.id asc";

        //System.out.println("---->"+hql);
        //System.out.println("---->" + hql.replaceAll(":gi", "'" + VicThreadScope.gi.get() + "," + "'").replaceAll(":ui", "'" + VicThreadScope.ui.get() + "'").replaceAll("\n", ""));
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(vicQuery.getFirstResult());
        query.setMaxResults(vicQuery.getMaxResults());
        setTenancyParameters(query);
        return query.getResultList();
    }

}
