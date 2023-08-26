package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.*;
import br.com.munif.framework.vicente.domain.SimpleBaseEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author munif
 */
@Service
@Scope("prototype")
public abstract class GatewayService<T extends SimpleBaseEntity> implements VicServiceable<T> {


    private final String url;

    protected GatewayService(String url) {
        this.url = url;
    }

    @Override
    public T loadNoTenancy(String id) {
        return getT(restTemplate().exchange(url + "/" + id, HttpMethod.GET, VicRest.getHttpEntity(), VicResponse.class).getBody().getData());
    }

    @Override
    public void delete(T entity) {
        SimpleBaseEntity ent = entity;
        restTemplate().exchange(url + "/" + ent.getId(), HttpMethod.DELETE, VicRest.getHttpEntity(), VicResponse.class);
    }

    @Override
    public T load(String id) {
        return getT(restTemplate().exchange(url + "/" + id, HttpMethod.GET, VicRest.getHttpEntity(), VicResponse.class).getBody().getData());
    }

    @Override
    public T save(T model) {
        SimpleBaseEntity ent = model;
        if (model.getId() != null)
            return getT(restTemplate().exchange(url + "/" + ent.getId(), HttpMethod.PUT, VicRest.getHttpEntity(model), VicResponse.class).getBody().getData());
        return getT(restTemplate().exchange(url, HttpMethod.POST, VicRest.getHttpEntity(model), VicResponse.class).getBody().getData());
    }

    @Override
    public void patch(Map<String, Object> map) {
        restTemplate().exchange(url + "/" + map.get("id"), HttpMethod.PATCH, VicRest.getHttpEntity(map), VicResponse.class);
    }

    @Override
    public T patchReturning(Map<String, Object> map) {
        return getT(restTemplate().exchange(url + "/returning" + map.get("id"), HttpMethod.PATCH, VicRest.getHttpEntity(map), VicResponse.class).getBody().getData());
    }

    @Override
    public List<T> findByHql(VicQuery query) {
        VicResponse vicResponse = restTemplate().postForObject(url + "/vquery", VicRest.getHttpEntity(query), VicResponse.class);
        Map data = (Map) vicResponse.getData();
        List values = (List) data.get("values");
        return (List<T>) values.stream().map(value -> getT(value)).collect(Collectors.toList());
    }

    private T getT(Object value) {
        return new VicObjectMapper().convertValue(value, clazz());
    }

    @Override
    public List<T> findByHqlNoTenancy(VicQuery query) {
        VicResponse vicResponse = restTemplate().postForObject(url + "/vquery", VicRest.getHttpEntity(query), VicResponse.class);
        Map data = (Map) vicResponse.getData();
        List values = (List) data.get("values");
        return (List<T>) values.stream().map(value -> getT(value)).collect(Collectors.toList());
    }

    @Override
    public String draw(String id) {
        return restTemplate().exchange(url + "/draw/" + id, HttpMethod.GET, VicRest.getHttpEntity(), String.class).getBody();
    }

    @SuppressWarnings("unchecked")
    private Class<T> clazz() {
        return (Class<T>) Utils.inferGenericType(getClass());
    }

    @Override
    public T newEntity() {
        try {
            return clazz().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean isNew(String id) {
        return null;
    }

    public RestTemplate restTemplate() {
        return VicRest.template();
    }
}

//88B797E428E850E5494404A5
//88B797E428E850E5494404A5
