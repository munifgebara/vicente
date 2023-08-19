package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.domain.SimpleBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
@Service
@Scope("prototype")
public abstract class MongoService<T extends SimpleBaseEntity> implements VicServiceable<T> {

    protected final MongoRepository<T, String> repository;

    @Autowired
    protected MongoTemplate mongoTemplate;

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
    public List<T> findByHql(VicQuery vicQuery) {
        Query m = getQuery(vicQuery.getQuery());
        return mongoTemplate.find(m, clazz());
    }

    private Query getQuery(VQuery vquery) {
        Query m = getMainQuery();
        if (vquery.getCriteria() != null) {
            Criteria where = getCriteria(vquery);
            m = m.addCriteria(where);
        }
        for (VQuery subQuery : vquery.getSubQuerys()) {
            Criteria where = getCriteria(subQuery);
            m = m.addCriteria(where);
        }
        return m;
    }

    private static Criteria getCriteria(VQuery subQuery) {
        Criteria where = Criteria.where(String.valueOf(subQuery.getCriteria().getField()));
        switch (subQuery.getCriteria().getComparisonOperator()) {
            case GREATER_EQUAL:
                where = where.gte(subQuery.getCriteria().getValue());
                break;
            case GREATER:
                where = where.gt(subQuery.getCriteria().getValue());
                break;
            case LOWER_EQUAL:
                where = where.lte(subQuery.getCriteria().getValue());
                break;
            case LOWER:
                where = where.lt(subQuery.getCriteria().getValue());
                break;
            case CONTAINS:
                where = where.regex(String.valueOf(subQuery.getCriteria().getValue()));
                break;
            case NOT_CONTAINS:
                where = where.not().regex(String.valueOf(subQuery.getCriteria().getValue()));
                break;
            case STARTS_WITH:
                where = where.regex("/^" + subQuery.getCriteria().getValue() + "/");
                break;
            case ENDS_WITH:
                where = where.regex("/" + subQuery.getCriteria().getValue() + "/");
                break;
            case NOT_EQUAL:
                where = where.not().is(subQuery.getCriteria().getValue());
                break;
            default:
                where = where.is(subQuery.getCriteria().getValue());
        }
        return where;
    }

    private Query getMainQuery() {
        Query m = new Query();
        m = m.addCriteria(Criteria.where("ui").is(VicThreadScope.ui.get()));
        return m;
    }

    @Override
    public List<T> findByHqlNoTenancy(VicQuery query) {
        Query m = getQuery(query.getQuery());
        return mongoTemplate.find(m, clazz());
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
