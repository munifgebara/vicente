/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.application.search.VicSmartSearch;
import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Hibernate;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
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


    public List<T> findAllNoTenancy() {
        return super.findAll();
    }

    @Override
    public List<T> findAll() {
        Class<T> domainClass = this.getDomainClass();
        boolean assignableFrom2 = BaseEntity.class.isAssignableFrom(domainClass);
        if (!assignableFrom2) {
            return super.findAll();
        }
        String hql = "FROM " + domainClass.getSimpleName() + " obj where \n"
                + "   (obj.ui=:ui and mod(obj.rights/64,8)/4>=1) \n"
                + "or (:gi like concat('%',obj.gi,',%') and mod(obj.rights/8,8)/4>=1) \n"
                + "or (1=1        and mod(obj.rights  ,8)/4>=1)";
        //System.out.println("---->"+hql);
       //System.out.println("---->" + hql.replaceAll(":gi", "'" + VicThreadScope.gi.get() + "," + "'").replaceAll(":ui", "'" + VicThreadScope.ui.get() + "'").replaceAll("\n", ""));

        Query createQuery = entityManager.createQuery(hql);
        createQuery.setParameter("ui", VicThreadScope.ui.get());
        createQuery.setParameter("gi", VicThreadScope.gi.get() + ",");
        return createQuery.getResultList();
    }

    public List<T> findAllNoPublic() {
        Class<T> domainClass = this.getDomainClass();
        boolean assignableFrom2 = BaseEntity.class.isAssignableFrom(domainClass);
        if (!assignableFrom2) {
            return super.findAll();
        }
        String hql = "FROM " + domainClass.getSimpleName() + " obj where \n"
                + "   (obj.ui=:ui and mod(obj.rights/64,8)/4>=1) \n"
                + "or (:gi like concat('%',obj.gi,',%') and mod(obj.rights/8,8)/4>=1) \n";

        //System.out.println("---->"+hql);
       //System.out.println("---->" + hql.replaceAll(":gi", "'" + VicThreadScope.gi.get() + "," + "'").replaceAll(":ui", "'" + VicThreadScope.ui.get() + "'").replaceAll("\n", ""));

        Query createQuery = entityManager.createQuery(hql);
        createQuery.setParameter("ui", VicThreadScope.ui.get());
        createQuery.setParameter("gi", VicThreadScope.gi.get() + ",");
        return createQuery.getResultList();
    }

    @Override
    public List<T> findByHql(VicQuery query) {
        if (query.getMaxResults()==-1){
            query.setMaxResults(VicQuery.DEFAULT_QUERY_SIZE);
        }
        
        Class<T> domainClass = this.getDomainClass();
        boolean assignableFrom2 = BaseEntity.class.isAssignableFrom(domainClass);
        if (!assignableFrom2) {
            return super.findAll();
        }
        String hql = "FROM " + domainClass.getSimpleName() + " obj where \n"
                +"("+ (query.getHql()!=null?query.getHql():"1=1")+") and "
                + "("
                + "   (obj.ui=:ui and mod(obj.rights/64,8)/4>=1) \n"
                + "or (:gi like concat('%',obj.gi,',%') and mod(obj.rights/8,8)/4>=1) \n"
                + "or (1=1        and mod(obj.rights  ,8)/4>=1)"
                + ") "
                + " ORDER BY obj."+query.getOrderBy()+" , obj.id asc";
        
        //System.out.println("---->"+hql);
       //System.out.println("---->" + hql.replaceAll(":gi", "'" + VicThreadScope.gi.get() + "," + "'").replaceAll(":ui", "'" + VicThreadScope.ui.get() + "'").replaceAll("\n", ""));

        Query createQuery = entityManager.createQuery(hql);
        createQuery.setFirstResult(query.getFirstResult());
        createQuery.setMaxResults(query.getMaxResults());
        createQuery.setParameter("ui", VicThreadScope.ui.get());
        createQuery.setParameter("gi", VicThreadScope.gi.get() + ",");
        return createQuery.getResultList();
    }
    

}
