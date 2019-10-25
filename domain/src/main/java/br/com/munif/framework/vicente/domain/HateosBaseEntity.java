package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.ReflectionUtil;
import br.com.munif.framework.vicente.core.VicThreadScope;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class HateosBaseEntity {

//    TODO desacoplar classes quando for possível alterar o objeto por meio de um interceptor ao retornar o objeto que herda de BaseEntity
    @JsonGetter
    public Set<BaseLink> getLinks() {
        if (!VicThreadScope.enableHateoas.get() || MappedRoutes.routes.isEmpty()) return null;
        Set<BaseLink> baseLinks = MappedRoutes.routes.getOrDefault(getClass().getName(), new HashSet<>());
        return baseLinks.stream().map(link -> {
            Pattern compile = Pattern.compile("\\{[a-zA-Z0-9_.-\\\\+]*\\}");
            Matcher matcher = compile.matcher(link.getHref());

            String value = null;
            if (matcher.find()) {
                try {
                    String attribute = matcher.group().replaceAll("[^a-zA-Z0-9]", "");
                    Field field = ReflectionUtil.getField(getClass(), attribute);
                    if (field != null) {
                        field.setAccessible(true);
                        Object o = field.get(this);
                        value = String.valueOf(o);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (value != null) {
                return new BaseLink(link.getRel(), matcher.replaceFirst(value), link.getVerbs());
            }
            return link;
        }).collect(Collectors.toSet());
    }

}
