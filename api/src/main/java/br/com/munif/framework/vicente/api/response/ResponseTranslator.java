package br.com.munif.framework.vicente.api.response;

import br.com.munif.framework.vicente.api.errors.VicError;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseTranslator implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof VicError || body instanceof byte[])
            return body;

        if (body instanceof String)
            return body;

        if (body instanceof NoTranslate)
            return (((NoTranslate) body).data);

        HttpStatus status = HttpStatus.resolve(((ServletServerHttpResponse) response).getServletResponse().getStatus());
        return new VicResponse<>(status.name(), body);
    }
}
