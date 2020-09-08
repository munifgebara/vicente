package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author munif
 */
@NoRepositoryBean
public interface VicRepository<T extends BaseEntity> extends JpaRepository<T, Serializable> {
    List<T> findAllNoTenancy();

    List<T> findAllNoPublic();

    List<T> findByHql(VicQuery query);

    <G> List<G> genericFindByHql(VicQuery query);

    List<T> findByHqlNoTenancy(VicQuery query);

    void patch(Map<String, Object> map);

    T patchReturning(Map<String, Object> map);

    T load(String id);

    T loadNoTenancy(String id);

    Boolean isNew(String id);

    public void deleteByHQL(VicQuery vicQuery);
}
