package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicResponse;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.SimpleBaseEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
        return getT(restTemplate().exchange(url + "/" + id, HttpMethod.GET, getHttpEntity(), VicResponse.class).getBody().getData());
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
        restTemplate().exchange(url + "/" + ent.getId(), HttpMethod.DELETE, getHttpEntity(), VicResponse.class);
    }

    @Override
    public T load(String id) {
        return getT(restTemplate().exchange(url + "/" + id, HttpMethod.GET, getHttpEntity(), VicResponse.class).getBody().getData());
    }

    @Override
    public T save(T model) {
        SimpleBaseEntity ent = model;
        if (model.getId() != null)
            return getT(restTemplate().exchange(url + "/" + ent.getId(), HttpMethod.PUT, getHttpEntity(model), VicResponse.class).getBody().getData());
        return getT(restTemplate().exchange(url, HttpMethod.POST, getHttpEntity(model), VicResponse.class).getBody().getData());
    }

    @Override
    public void patch(Map<String, Object> map) {
        restTemplate().exchange(url + "/" + map.get("id"), HttpMethod.PATCH, getHttpEntity(map), VicResponse.class);
    }

    @Override
    public T patchReturning(Map<String, Object> map) {
        return getT(restTemplate().exchange(url + "/returning" + map.get("id"), HttpMethod.PATCH, getHttpEntity(map), VicResponse.class).getBody().getData());
    }

    @Override
    public List<T> findByHql(VicQuery query) {
        VicResponse vicResponse = restTemplate().postForObject(url + "/vquery", getHttpEntity(query), VicResponse.class);
        Map data = (Map) vicResponse.getData();
        List values = (List) data.get("values");
        return (List<T>) values.stream().map(value -> getT(value)).collect(Collectors.toList());
    }

    private T getT(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        Hibernate5Module hm = new Hibernate5Module();
        mapper
                .registerModule(hm)
                .registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new StdDateFormat());
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(value, clazz());
    }

    @Override
    public List<T> findByHqlNoTenancy(VicQuery query) {
        VicResponse vicResponse = restTemplate().postForObject(url + "/vquery", getHttpEntity(query), VicResponse.class);
        Map data = (Map) vicResponse.getData();
        List values = (List) data.get("values");
        return (List<T>) values.stream().map(value -> getT(value)).collect(Collectors.toList());
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
