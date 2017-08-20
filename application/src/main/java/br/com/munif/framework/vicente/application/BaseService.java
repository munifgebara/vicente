/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application;

import java.text.SimpleDateFormat;
import java.util.List;
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

    protected final JpaRepository<T, String> repository;

    @PersistenceContext
    protected EntityManager em;

    public BaseService(JpaRepository<T, String> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        List<T> result = repository.findAll();
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
        repository.flush();
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

}
