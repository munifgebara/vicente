package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.SimpleBaseEntity;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        return restTemplate().exchange(url + "/" + id, HttpMethod.GET, getHttpEntity(), clazz()).getBody();
    }

    private HttpEntity<?> getHttpEntity() {
        HttpHeaders httpHeaders = getHttpHeaders();
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<?> getHttpEntity(Object body) {
        HttpHeaders httpHeaders = getHttpHeaders();
        return new HttpEntity<>(body, httpHeaders);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", VicThreadScope.token.get());
//        httpHeaders.set("Origin", VicThreadScope.origin.get());
//        httpHeaders.set("group", VicThreadScope.cg.get());
//        httpHeaders.set("language", VicThreadScope.language.get());
//        httpHeaders.set("timezone", VicThreadScope.timezone.get());
        return httpHeaders;
    }

    @Override
    public void delete(T entity) {
        SimpleBaseEntity ent = entity;
        restTemplate().exchange(url + "/" + ent.getId(), HttpMethod.DELETE, getHttpEntity(), clazz());
    }

    @Override
    public T load(String id) {
        return restTemplate().exchange(url + "/" + id, HttpMethod.GET, getHttpEntity(), clazz()).getBody();
    }

    @Override
    public T save(T model) {
        return restTemplate().exchange(url, HttpMethod.POST, getHttpEntity(model), clazz()).getBody();
    }

    @Override
    public void patch(Map<String, Object> map) {
        restTemplate().exchange(url + "/" + map.get("id"), HttpMethod.PATCH, getHttpEntity(map), clazz());
    }

    @Override
    public T patchReturning(Map<String, Object> map) {
        return restTemplate().exchange(url + "/returning" + map.get("id"), HttpMethod.PATCH, getHttpEntity(map), clazz()).getBody();
    }

    @Override
    public List<T> findByHql(VicQuery query) {
        return restTemplate().postForObject(url + "/vquery", getHttpEntity(query), List.class);
    }

    @Override
    public List<T> findByHqlNoTenancy(VicQuery query) {
        return restTemplate().exchange(url + "/vquery", HttpMethod.POST, getHttpEntity(query), List.class).getBody();
    }

    @Override
    public String draw(String id) {
        return restTemplate().exchange(url + "/draw/" + id, HttpMethod.GET, getHttpEntity(), String.class).getBody();
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
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }
}

//88B797E428E850E5494404A5
//88B797E428E850E5494404A5
