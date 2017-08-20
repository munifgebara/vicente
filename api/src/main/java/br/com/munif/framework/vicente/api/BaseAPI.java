/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.core.VicReturn;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author munif
 */
@RestController
public class BaseAPI<T extends BaseEntity> {

    private final BaseService<T> service;

    public BaseAPI(BaseService<T> service) {
        this.service = service;
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public T delete(@PathVariable String id) {
        T entity = service.view(id);
        service.delete(entity);
        return entity;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public T save(@RequestBody T model) {
        beforeSave(model);
        T entity = service.save(model);
        return entity;
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public T update(@PathVariable("id") String id, @RequestBody T model) {
        beforeUpdate(id, model);
        T entity = service.save(model);
        return entity;
    }

    protected void beforeSave(T model) {

    }

    protected void beforeUpdate(String id, T model) {

    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public VicReturn<T> findAll() {
        List<T> findAll = service.findAll();
        return new VicReturn<T>(findAll);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T load(@PathVariable String id) {
        T view = service.view(id);
        return view;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public T initialState() {
        return newEntity();
    }

    protected T newEntity() {
        return null;
    }



}
