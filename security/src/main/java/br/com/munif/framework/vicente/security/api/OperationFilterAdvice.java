package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.VicenteErrorOnRequestException;
import br.com.munif.framework.vicente.api.VicenteRightsException;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.VicThreadScopeOptions;
import br.com.munif.framework.vicente.security.domain.profile.ForwardRequest;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;
import br.com.munif.framework.vicente.security.domain.profile.OperationType;
import br.com.munif.framework.vicente.security.domain.profile.RequestAction;
import br.com.munif.framework.vicente.security.service.interfaces.IOperationFilterService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
public class OperationFilterAdvice implements ResponseBodyAdvice<Object> {
    private final IOperationFilterService operationFilterService;

    public OperationFilterAdvice(IOperationFilterService operationFilterService) {
        this.operationFilterService = operationFilterService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String s = VicThreadScope.token.get();
        String s1 = VicThreadScope.ui.get();
        if (s != null && s1 != null)
            filterRequest(o, methodParameter, s, s1);
        return o;
    }


    public boolean filterRequest(Object body, MethodParameter methodParameter, String tokenValue, String login) {
        String api = methodParameter.getContainingClass().getSimpleName();
        String method = methodParameter.getExecutable().getName();
        if (api.contains("$$")) {
            api = api.substring(0, api.indexOf("$$"));
        }
        OperationFilter operationFilter = operationFilterService.findByKeyAndLogin(api + "_" + method, login);
        for (RequestAction action : operationFilter.getActions()) {
            if (action instanceof ForwardRequest) {
                ForwardRequest forwardRequest = (ForwardRequest) action;
                HttpHeaders headers = new HttpHeaders();
                headers.set(forwardRequest.getAuthorizationHeaderName(), tokenValue);
                if (forwardRequest.getDefaultAuthorizationHeader() != null) {
                    headers.set(forwardRequest.getAuthorizationHeaderName(), forwardRequest.getDefaultAuthorizationHeader());
                }
                try {
                    restTemplate().exchange(forwardRequest.getUrl(), forwardRequest.getMethod(), new HttpEntity<>(body, headers), Map.class);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.getValue()) {
                        throw new VicenteErrorOnRequestException("Was not possible to request the " + forwardRequest.getUrl());
                    }
                }
            }
        }
        if (OperationType.DENY.equals(operationFilter.getOperationType())) {
            throw new VicenteRightsException("You do not have the rights to request this resource.");
        }
        return true;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
