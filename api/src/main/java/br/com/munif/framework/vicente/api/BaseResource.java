package br.com.munif.framework.vicente.api;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.core.ReflectionUtil;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicReturn;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author munif
 */
@RestController
@Scope("prototype")
public class BaseResource<T extends BaseEntity> {

    public BaseService<T> service;

    public BaseResource(VicServiceable service) {
        if (service instanceof BaseService) {
            this.service = (BaseService<T>) service;
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return mono(doDelete(id));
    }

    @Transactional
    protected ResponseEntity<Void> doDelete(String id) {
        T entity = service.loadNoTenancy(id);
        if (entity == null) {
            throw new VicenteNotFoundException("Not found");
        }
        if (!entity.canDelete()) {
            throw new VicenteRightsException("DELETE," + id + "," + entity.r());
        }
        beforeDelete(entity);
        service.delete(entity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<T>> save(@RequestBody @Valid T model) {
        return mono(doSave(model));
    }

    @Transactional
    public ResponseEntity<T> doSave(T model) {
        beforeSave(model);
        if (service.load(model.getId()) != null) {
            throw new VicenteCreateWithExistingIdException("create With Existing Id=" + model.getId());
        }

        T entity = service.save(model);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<T>> updateWithoutId(@RequestBody @Valid T model) {
        return mono(doUpdate(model));
    }

    private ResponseEntity<T> doUpdate(T model) {
        T entity = null;
        HttpStatus ht = HttpStatus.OK;
        T oldEntity = service.loadNoTenancy(model.getId());
        if (oldEntity != null) {
            if (!oldEntity.canUpdate()) {
                throw new VicenteRightsException("PUT," + oldEntity.getId() + "," + oldEntity.r());
            }
            beforeUpdate(model.getId(), model);
            BaseEntityHelper.overwriteJsonIgnoreFields(model, oldEntity);
            entity = service.save(model);
        } else {
            beforeSave(model);

            entity = service.save(model);
            ht = HttpStatus.CREATED;
        }
        return new ResponseEntity(entity, ht);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<T>> updateWithId(@PathVariable("id") String id, @RequestBody @Valid T model) {
        return mono(doUpdateWithId(id, model));
    }

    @Transactional
    public ResponseEntity<T> doUpdateWithId(String id, T model) {
        model.setId(id);
        return doUpdate(model);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> patch(@PathVariable("id") String id, @RequestBody @Valid Map model) {
        return mono(doPatch(id, model));
    }

    @Transactional
    public ResponseEntity<Void> doPatch(String id, Map model) {
        model.put("id", id);
        service.patch(model);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/returning/{id}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> patchReturning(@PathVariable("id") String id, @RequestBody @Valid Map model) {
        return mono(doPatchReturning(id, model));
    }

    @Transactional
    public ResponseEntity doPatchReturning(String id, Map model) {
        model.put("id", id);
        return ResponseEntity.ok(service.patchReturning(model));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VicReturn<T>>> findByHQL(HttpServletRequest request, VicQuery query) {
        return mono(doFindByHQL(request, query));
    }

    @Transactional
    public ResponseEntity<VicReturn<T>> doFindByHQL(HttpServletRequest request, VicQuery query) {
        return getVicReturnByVQuery(query);
    }

    @PostMapping(value = "/vquery", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<VicReturn<T>>> findByVQuery(@RequestBody VicQuery query) {
        return mono(doFindByVQuery(query));
    }


    @Transactional
    public ResponseEntity<VicReturn<T>> doFindByVQuery(@RequestBody VicQuery query) {
        return getVicReturnByVQuery(query);
    }

    @Transactional
    public ResponseEntity<VicReturn<T>> getVicReturnByVQuery(@RequestBody VicQuery query) {
        if (query.getHql() == null || query.getHql().trim().isEmpty()) {
            query.setHql(VicQuery.DEFAULT_QUERY);
        }
        if (query.getMaxResults() == -1) {
            query.setMaxResults(this.getDefaultSize());
        }
        int maxResults = query.getMaxResults();
        query.setMaxResults(maxResults + 1);
        query.setHql(query.getHql().replace("\"", ""));
        Set<T> result = new HashSet<>(service.findByHql(query));
        boolean hasMore = result.size() > maxResults;
        if (hasMore) {
            result.remove(maxResults);
        }
        if (query.getQuery() != null && query.getQuery().getFields() != null) {
            String[] fields = query.getQuery().getFields();
            Set<Map<String, Object>> collect = result.stream().map(s -> getFields(fields, s)).collect(Collectors.toSet());
            return ResponseEntity.ok(new VicReturn(collect, collect.size(), query.getFirstResult(), hasMore));
        }
        return ResponseEntity.ok(new VicReturn<T>(result, result.size(), query.getFirstResult(), hasMore));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> load(@PathVariable String id, @RequestParam(required = false) String fields) {
        return mono(doLoad(id, fields));
    }

    @Transactional(readOnly = true)
    public ResponseEntity doLoad(String id, String fields) {
        T view = service.loadNoTenancy(id);
        if (view == null) {
            throw new VicenteNotFoundException("Not found");
        }
        if (!view.canRead()) {
            throw new VicenteRightsException("READ," + id + "," + view.r());
        }

        beforeReturnOne(view);
        if (fields != null) {
            Map<String, Object> stringObjectMap = getFields(fields, view);
            return new ResponseEntity(stringObjectMap, HttpStatus.OK);
        }
        return ResponseEntity.ok(view);
    }

    private Map<String, Object> getFields(String fields, T view) {
        String[] split = fields.split(",");
        return getFields(split, view);
    }

    private Map<String, Object> getFields(String[] fields, T view) {
        return ReflectionUtil.objectFieldsToMap(fields, view);
    }


    @GetMapping(value = "/is-new/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Boolean>> isNew(@PathVariable String id) {
        return mono(isNewData(id));
    }

    @Transactional
    public ResponseEntity<Boolean> isNewData(@PathVariable String id) {
        return ResponseEntity.ok(service.isNew(id));
    }

    @ResponseBody
    @GetMapping(value = "/draw/{id}", produces = "image/svg+xml")
    public Mono<ResponseEntity<String>> draw(@PathVariable String id) {
        return mono(doDraw(id));
    }

    @Transactional
    public ResponseEntity<String> doDraw(@PathVariable String id) {
        String svg = service.draw(id);
        return ResponseEntity.ok(svg);
    }

    @GetMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<T>> initialState() {
        return mono(getInitialState());
    }

    public ResponseEntity<T> getInitialState() {
        return ResponseEntity.ok(service.newEntity());
    }

    public int getDefaultSize() {
        return 20;
    }

    protected void beforeSave(T model) {
    }

    protected void beforeUpdate(String id, T model) {
    }

    protected void beforeReturnOne(T view) {
    }

    protected void beforeDelete(T entity) {
    }

    public <T> Mono<T> mono(T callable) {
        return Mono.just(callable).publishOn(Schedulers.elastic());
    }

    public <T> Flux<T> flux(Iterable<T> callable) {
        return Flux.fromIterable(callable).publishOn(Schedulers.elastic());
    }

}
