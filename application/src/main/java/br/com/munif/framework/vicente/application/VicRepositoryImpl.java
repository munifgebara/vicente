/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.Utils;
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

    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(Specification<S> spec, Class<S> domainClass) {
        System.out.println("----> super.getCountQuery(spec,domainClass) ");
        return super.getCountQuery(spec, domainClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected TypedQuery<Long> getCountQuery(Specification<T> spec) {
        System.out.println("----> super.getCountQuery(spec) ");
        return super.getCountQuery(spec); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {
        System.out.println("----> super.getQuery(spec,domainClass,sort) ");
        return super.getQuery(spec, domainClass, sort); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {
        System.out.println("----> super.getQuery(spec,sort) ");
        return super.getQuery(spec, sort); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Pageable pageable) {
        System.out.println("----> super.getQuery(spec,domainClass,pageable) ");
        return super.getQuery(spec, domainClass, pageable); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {
        System.out.println("----> super.getQuery(spec,pageable) ");
        return super.getQuery(spec, pageable); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected <S extends T> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable, Specification<S> spec) {
        System.out.println("----> super.readPage(query,domainClass,pageable,spec) ");
        return super.readPage(query, domainClass, pageable, spec); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Page<T> readPage(TypedQuery<T> query, Pageable pageable, Specification<T> spec) {
        System.out.println("----> super.readPage(query,pageable,spec) ");
        return super.readPage(query, pageable, spec); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void flush() {
        System.out.println("----> super.flush() ");
        super.flush(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        System.out.println("----> super.save(entities) ");
        return super.save(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        System.out.println("----> super.saveAndFlush(entity) ");
        return super.saveAndFlush(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> S save(S entity) {
        System.out.println("----> super.save(entity) ");
        return super.save(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count(Specification<T> spec) {
        System.out.println("----> super.count(spec) ");
        return super.count(spec); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        System.out.println("----> super.count() ");
        return super.count(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        System.out.println("----> super.findAll(example,pageable) ");
        return super.findAll(example, pageable); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        System.out.println("----> super.findAll(example,sort) ");
        return super.findAll(example, sort); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        System.out.println("----> super.findAll(example) ");
        return super.findAll(example); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        System.out.println("----> super.exists(example) ");
        return super.exists(example); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        System.out.println("----> super.count(example) ");
        return super.count(example); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <S extends T> S findOne(Example<S> example) {
        System.out.println("----> super.findOne(example) ");
        S findOne = super.findOne(example); //To change body of generated methods, choose Tools | Templates.
        Class<T> domainClass = this.getDomainClass();
        List<Field> allFields = Utils.getAllFields(domainClass);
        for (Field f:allFields){
            f.setAccessible(true);
            if (f.isAnnotationPresent(OneToMany.class)||f.isAnnotationPresent(ManyToMany.class)){
                try {
                    Hibernate.initialize(f.get(findOne));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(VicRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(VicRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
        return findOne;
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        System.out.println("----> super.findAll(spec,sort) ");
        return super.findAll(spec, sort); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        System.out.println("----> super.findAll(spec,pageable) ");
        return super.findAll(spec, pageable); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        System.out.println("----> super.findAll(spec) ");
        return super.findAll(spec); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T findOne(Specification<T> spec) {
        System.out.println("----> super.findOne(spec) ");
        return super.findOne(spec); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        System.out.println("----> super.findAll(pageable) ");
        return super.findAll(pageable); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> findAll(Sort sort) {
        System.out.println("----> super.findAll(sort) ");
        return super.findAll(sort); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> findAll(Iterable<Serializable> ids) {
        System.out.println("----> super.findAll(ids) ");
        return super.findAll(ids); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> findAll() {
        System.out.println("----> super.findAll() ");
        Class<T> domainClass = this.getDomainClass();
        boolean assignableFrom2 = BaseEntity.class.isAssignableFrom(domainClass);
        if (!assignableFrom2){
            return super.findAll();
        }
        
        Query createQuery = entityManager.createQuery("FROM " + domainClass.getSimpleName()+ " obj where \n"
                  // 123456789012345678901234567890
                + "   (obj.ui=:ui and mod(obj.rights/64,8)/4>=1) \n"
                + "or (:gi like concat('%',obj.gi,',%') and mod(obj.rights/8,8)/4>=1) \n"
                + "or (1=1        and mod(obj.rights  ,8)/4>=1)");
        createQuery.setParameter("ui", VicThreadScope.ui.get());
        createQuery.setParameter("gi", VicThreadScope.gi.get()+",");
        return createQuery.getResultList();
    }

    @Override
    public boolean exists(Serializable id) {
        System.out.println("----> super.exists(id) ");
        return super.exists(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T getOne(Serializable id) {
        System.out.println("----> super.getOne(id) ");
        return super.getOne(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Map<String, Object> getQueryHints() {
        System.out.println("----> super.getQueryHints() ");
        return super.getQueryHints(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T findOne(Serializable id) {
        System.out.println("----> super.findOne(id) ");
        T findOne = super.findOne(id); //To change body of generated methods, choose Tools | Templates.
        Class<T> domainClass = this.getDomainClass();
        List<Field> allFields = Utils.getAllFields(domainClass);
        for (Field f:allFields){
            f.setAccessible(true);
            if (f.isAnnotationPresent(OneToMany.class)||f.isAnnotationPresent(ManyToMany.class)){
                try {
                    Hibernate.initialize(f.get(findOne));
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(VicRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(VicRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return findOne;
    }

    @Override
    public void deleteAllInBatch() {
        System.out.println("----> super.deleteAllInBatch() ");
        super.deleteAllInBatch(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        System.out.println("----> super.deleteAll() ");
        super.deleteAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        System.out.println("----> super.deleteInBatch(entities) ");
        super.deleteInBatch(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        System.out.println("----> super.delete(entities) ");
        super.delete(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(T entity) {
        System.out.println("----> super.delete(entity) ");
        super.delete(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Serializable id) {
        System.out.println("----> super.delete(id) ");
        super.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Class<T> getDomainClass() {
        System.out.println("----> super.getDomainClass() ");
        return super.getDomainClass(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected CrudMethodMetadata getRepositoryMethodMetadata() {
        System.out.println("----> super.getRepositoryMethodMetadata() ");
        return super.getRepositoryMethodMetadata(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
        System.out.println("----> super.setRepositoryMethodMetadata(crudMethodMetadata) ");
        super.setRepositoryMethodMetadata(crudMethodMetadata); //To change body of generated methods, choose Tools | Templates.
    }

}
