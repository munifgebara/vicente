package br.com.munif.framework.vicente.application;

import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

/**
 *
 * @author munif
 */
@NoRepositoryBean
public interface VicRepository<T>  extends PagingAndSortingRepository<T, String>{
    
}