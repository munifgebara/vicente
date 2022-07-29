package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author munif
 */
@NoRepositoryBean
public interface VicReactiveRepository<T extends BaseEntity> extends ReactiveSortingRepository<T, Serializable> {
    Flux<T> findAllNoTenancy();

    Flux<T> findAllNoPublic();

    Flux<T> findByHql(VicQuery query);

    Query getQuery(VicQuery vicQuery, Class clazz);

    Flux<T> findByHqlNoTenancy(VicQuery query);

    void patch(Map<String, Object> map);

    Mono<T> patchReturning(Map<String, Object> map);

    Mono<T> load(String id);

    Mono<T> loadNoTenancy(String id);

    Mono<Boolean> isNew(String id);

    public void deleteByHQL(VicQuery vicQuery);
}
