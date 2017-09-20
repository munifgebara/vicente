/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicReturn;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public T save(@RequestBody @Valid T model) {
        beforeSave(model);
        if (service.view(model.getId())!=null){
            throw new VicenteCreateWithExistingIdException("create With Existing Id="+model.getId());
        }
        
        T entity = service.save(model);
        return entity;
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public T update(@PathVariable("id") String id, @RequestBody @Valid T model) {
        beforeUpdate(id, model);
        T entity = service.save(model);
        return entity;
    }

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<T> update2(@RequestBody @Valid T model) {
        try{
        HttpStatus ht=HttpStatus.OK;
        if (service.view(model.getId())!=null){
            model = update(model.getId(), model);
        } else {
            model = service.save(model);
            ht=HttpStatus.CREATED;
        }
        return new ResponseEntity(model, ht);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        
   }

    protected void beforeSave(T model) {

    }

    protected void beforeUpdate(String id, T model) {

    }

//    @Transactional
//    @RequestMapping(method = RequestMethod.GET)
//    public VicReturn<T> findAll() {
//        List<T> findAll = service.findAll();
//        return new VicReturn<T>(findAll);
//    }
    
    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public VicReturn<T> findHQL(HttpServletRequest request, VicQuery query) {
        query.setHql(query.getHql().replace("\"", ""));
        List<T> result = service.findByHql(query);
        return new VicReturn<T>(result);
    }

    
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T load(@PathVariable String id) {
        T view = service.view(id);
        if (view == null) {
            throw new VicenteNotFoundException("Not found");
        };
        return view;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public T initialState() {
        return service.newEntity();
    }

}
