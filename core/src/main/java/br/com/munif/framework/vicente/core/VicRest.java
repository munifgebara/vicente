package br.com.munif.framework.vicente.core;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VicRest {
    public static RestTemplate template() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    public static HttpEntity<?> getHttpEntity(Object body) {
        HttpHeaders httpHeaders = getHttpHeaders();
        return getHttpEntity(body, httpHeaders);
    }

    public static HttpEntity<Object> getHttpEntity(Object body, HttpHeaders httpHeaders) {
        return new HttpEntity<>(body, httpHeaders);
    }


    public static HttpEntity<?> getHttpEntity() {
        HttpHeaders httpHeaders = VicRest.getHttpHeaders();
        return new HttpEntity<>(httpHeaders);
    }


    public static HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", VicThreadScope.token.get());
        httpHeaders.set("origin", VicThreadScope.origin.get());
        httpHeaders.set("group", VicThreadScope.cg.get());
        httpHeaders.set("language", VicThreadScope.language.get());
        httpHeaders.set("timezone", VicThreadScope.timezone.get());
        return httpHeaders;
    }


    public static Map<String, String> getHttpHeadersMap() {
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", VicThreadScope.token.get());
        httpHeaders.put("origin", VicThreadScope.origin.get());
        httpHeaders.put("group", VicThreadScope.cg.get());
        httpHeaders.put("language", VicThreadScope.language.get());
        httpHeaders.put("timezone", VicThreadScope.timezone.get());
        return httpHeaders;
    }

    public static HttpHeaders getHttpHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                httpHeaders.set(key, headers.get(key));
            }

        }
        return httpHeaders;
    }

}
