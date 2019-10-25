package br.com.munif.framework.vicente.api.hateoas;

import br.com.munif.framework.vicente.api.BaseAPI;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class HateoasTranslator implements ResponseBodyAdvice<ResponseEntity> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getContainingClass().getSuperclass() == BaseAPI.class && methodParameter.getParameterType() == ResponseEntity.class;
    }

    @Override
    public ResponseEntity beforeBodyWrite(ResponseEntity responseEntity, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return responseEntity;
    }
}