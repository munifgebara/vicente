package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author munif
 */
@NoRepositoryBean
public interface VicRepository<T extends BaseEntity> extends JpaRepository<T, Serializable> {

    List<T> findAllNoTenancy();
    
    List<T> findAllNoPublic();
    
    List<T> findByHql(VicQuery query);

}
