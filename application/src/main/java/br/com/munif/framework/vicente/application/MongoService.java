package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.application.victenancyfields.VicFieldRepository;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldValueRepository;
import br.com.munif.framework.vicente.core.*;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldType;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldValue;
import br.com.munif.framework.vicente.domain.tenancyfields.VicTenancyFieldsBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
@Service
@Scope("prototype")
public abstract class MongoService<T> implements VicServiceable<T> {

    protected final MongoRepository<T, String> repository;
    public MongoService(MongoRepository<T, String> repository) {
        this.repository = repository;
    }

    public MongoRepository<T, String> getRepository() {
        return repository;
    }


    @Override
    public T loadNoTenancy(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public T load(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public T save(T model) {
        return repository.save(model);
    }

    @Override
    public void patch(Map<String, Object> map) {

    }

    @Override
    public T patchReturning(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<T> findByHql(VicQuery query) {
        return null;
    }
    @Override
    public List<T> findByHqlNoTenancy(VicQuery query) {
        return null;
    }

    @Override
    public String draw(String id) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private Class<T> clazz() {
        return (Class<T>) Utils.inferGenericType(getClass());
    }
    @Override
    public T newEntity() {
        try {
            return clazz().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean isNew(String id) {
        return null;
    }
}

//88B797E428E850E5494404A5
//88B797E428E850E5494404A5
