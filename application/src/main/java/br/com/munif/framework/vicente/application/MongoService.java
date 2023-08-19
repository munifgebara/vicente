package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.CriteriaField;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.domain.SimpleBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
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
        Query m = getQuery(vicQuery.getQuery())
                .limit(vicQuery.getMaxResults())
                .skip(vicQuery.getFirstResult());
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
        String field = String.valueOf(subQuery.getCriteria().getField());
        field = field.replaceAll("\\(","");
        field = field.replaceAll("\\)","");
        field = field.replaceAll("upper","");
        field = field.replaceAll("lower","");
        Criteria where = Criteria.where(field);
        switch (subQuery.getCriteria().getComparisonOperator()) {
            case GREATER_EQUAL:
                where = where.gte(getValue(subQuery));
                break;
            case GREATER:
                where = where.gt(getValue(subQuery));
                break;
            case LOWER_EQUAL:
                where = where.lte(getValue(subQuery));
                break;
            case LOWER:
                where = where.lt(getValue(subQuery));
                break;
            case CONTAINS:
                where = where.regex(String.valueOf(getValue(subQuery)));
                break;
            case NOT_CONTAINS:
                where = where.not().regex(String.valueOf(getValue(subQuery)));
                break;
            case STARTS_WITH:
                where = where.regex("/^" + getValue(subQuery) + "/");
                break;
            case ENDS_WITH:
                where = where.regex("/" + getValue(subQuery) + "/");
                break;
            case NOT_EQUAL:
                where = where.not().is(getValue(subQuery));
                break;
            default:
                where = where.is(getValue(subQuery));
        }
        return where;
    }

    private static Object getValue(VQuery subQuery) {
        if (subQuery.getCriteria().getValue() instanceof Long)
            return new java.util.Date((Long) subQuery.getCriteria().getValue());
        else if (subQuery.getCriteria().getValue() instanceof CriteriaField)
            return ((CriteriaField) subQuery.getCriteria().getValue()).getField();
        else if (subQuery.getCriteria().getValue() instanceof LinkedHashMap)
            return ((LinkedHashMap) subQuery.getCriteria().getValue()).get("field");
        return subQuery.getCriteria().getValue();
    }

    private Query getMainQuery() {
        Query m = new Query();
//        m = m.addCriteria(Criteria.where("ui").is(VicThreadScope.ui.get()));
        return m;
    }

    @Override
    public List<T> findByHqlNoTenancy(VicQuery vicQuery) {
        Query m = getQuery(vicQuery.getQuery())
                .limit(vicQuery.getMaxResults())
                .skip(vicQuery.getFirstResult());
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
