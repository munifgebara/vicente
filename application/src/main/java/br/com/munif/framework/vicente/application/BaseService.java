/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author munif
 */
@Service
@Scope("prototype")
public abstract class BaseService<T> {

    protected final VicRepository<T> repository;

    @PersistenceContext
    protected EntityManager em;

    public BaseService(VicRepository<T> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        Iterable<T> findAll = repository.findAll();
        
        List<T> result = new ArrayList<T>();
        for (T r:findAll){
            result.add(r);
        }
        
        return result;
    }

    @Transactional(readOnly = true)
    public T view(String id) {
        T entity = repository.findOne(id);
        return entity;
    }

    @Transactional
    public void delete(T resource) {
        repository.delete(resource);
    }

    @Transactional
    public T save(T resource) {
        T entity = repository.save(resource);
        return entity;
    }

    public void forceFlush() {
//        repository.flush();
    }

    @Transactional(readOnly = true)
    public List<T> find(Class classe, String hql, int maxResults) {
        String q = "from " + classe.getSimpleName() + " obj where " + hql;
        Query query = em.createQuery(q);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<T> find10primeiros(Class classe, String hql) {
        return find(classe, hql, 10);
    }

    public Long quantidade() {
        return repository.count();
    }
    
     public T newEntity() {
        try {
            return clazz().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(BaseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Class<T> clazz() {
        return (Class<T>) Utils.inferGenericType(getClass());
    }


}


//88B797E428E850E5494404A5 
//88B797E428E850E5494404A5