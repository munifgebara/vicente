package br.com.munif.framework.vicente.api.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class EndpointsListener implements ApplicationListener {

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Boolean isEnabled = Boolean.parseBoolean(env.getProperty("vicente.enable-hateoas"));
        if (!isEnabled) return;
        ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> map : handlerMethods.entrySet()) {
            Class<?> beanType = map.getValue().getBeanType();
            if (beanType.getGenericSuperclass() != null && beanType.getGenericSuperclass() instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) beanType.getGenericSuperclass()).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                    Type actualTypeArgument = actualTypeArguments[0];
                    Set<String> patterns = map.getKey().getPatternsCondition().getPatterns();
                    Set<RequestMethod> methods = map.getKey().getMethodsCondition().getMethods();
                    Iterator<RequestMethod> methodIterator = methods.iterator();
                    for (String str : patterns) {
                        Set<BaseLink> baseLinks = MappedRoutes.routes.computeIfAbsent(actualTypeArgument.getTypeName(), k -> new HashSet<>());
                        BaseLink baseLink = baseLinks.stream().filter(b -> b.getHref().equals(str)).findFirst().orElse(null);
                        RequestMethod next = methodIterator.next();
                        if (baseLink == null) {
                            baseLinks.add(new BaseLink("self", str, next.name()));
                        } else {
                            baseLink.getVerbs().add(next.name());
                        }
                    }
                }
            }

        }
    }
}
