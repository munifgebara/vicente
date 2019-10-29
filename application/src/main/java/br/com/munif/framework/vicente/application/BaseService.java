package br.com.munif.framework.vicente.application;

import br.com.munif.framework.vicente.application.victenancyfields.VicFieldRepository;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldValueRepository;
import br.com.munif.framework.vicente.core.Utils;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicScriptEngine;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldType;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldValue;
import br.com.munif.framework.vicente.domain.tenancyfields.VicTenancyFieldsBaseEntity;
import io.reactivex.Single;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
@Service
@Scope("prototype")
public abstract class BaseService<T extends BaseEntity> {

    protected final VicRepository<T> repository;
    @Autowired
    protected VicFieldValueRepository vicFieldValueRepository;
    @Autowired
    protected VicFieldRepository vicFieldRepository;

    @PersistenceContext
    protected EntityManager em;

    public BaseService(VicRepository<T> repository) {
        this.repository = repository;
    }

    public VicRepository<T> getRepository() {
        return repository;
    }

    public EntityManager getEm() {
        return em;
    }

    @Transactional(readOnly = true)
    public List<T> findAllNoTenancy() {
        List<T> result = repository.findAllNoTenancy();
        readVicTenancyFields(result);
        return result;
    }

    @Transactional(readOnly = true)
    public Single<List<T>> asyncFindAllNoTenancy() {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(findAllNoTenancy());
        });
    }

    @Transactional(readOnly = true)
    public List<T> findAllNoPublic() {
        List<T> result = repository.findAllNoPublic();
        readVicTenancyFields(result);
        return result;
    }

    @Transactional(readOnly = true)
    public Single<List<T>> asyncFindAllNoPublic() {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(findAllNoPublic());
        });
    }

    @Transactional(readOnly = true)
    public List<T> findByHql(VicQuery query) {
        List<T> result = repository.findByHql(query);
        readVicTenancyFields(result);
        return result;
    }

    @Transactional(readOnly = true)
    public Single<List<T>> asyncFindByHql(VicQuery query) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(findByHql(query));
        });
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        List<T> result = repository.findAll();
        readVicTenancyFields(result);
        return result;
    }

    @Transactional(readOnly = true)
    public Single<List<T>> asyncFindAll() {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(findAll());
        });
    }

    @Transactional(readOnly = true)
    public T load(String id) {
        T entity = repository.load(id);
        readVicTenancyFields(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public Single<T> asyncLoad(String id) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(load(id));
        });
    }

    @Transactional
    public void delete(T resource) {
        if (resource instanceof VicTenancyFieldsBaseEntity) {
            //deleteVicTenancyFields(resource);
        }
        repository.delete(resource);
    }

    @Transactional
    public Single<Void> asyncDelete(T resource) {
        return Single.create(singleEmitter -> {
            delete(resource);
            singleEmitter.onSuccess(null);
        });
    }

    @Transactional
    public T save(T resource) {
        if (resource != null) {
            resource.setUd(new Date());
        }
        T entity = repository.save(resource);
        if (entity instanceof VicTenancyFieldsBaseEntity) {
            saveVicTenancyFields(resource);
        }
        return entity;
    }

    @Transactional
    public Single<T> asyncSave(T resource) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(save(resource));
        });
    }

    @Transactional
    public void patch(Map<String, Object> map) {
        repository.patch(map);
    }

    @Transactional
    public Single<Void> asyncPatch(Map<String, Object> map) {
        return Single.create(singleEmitter -> {
            patch(map);
            singleEmitter.onSuccess(null);
        });
    }

    @Transactional
    public T patchReturning(Map<String, Object> map) {
        return repository.patchReturning(map);
    }

    @Transactional
    public Single<T> asyncPatchReturning(Map<String, Object> map) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(patchReturning(map));
        });
    }

    @Transactional(readOnly = true)
    public T findOne(String id) {
        return repository.load(id);
    }

    @Transactional(readOnly = true)
    public Single<T> asyncFindOne(String id) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(findOne(id));
        });
    }

    @Transactional(readOnly = true)
    public List<T> find(Class classe, String hql, int maxResults) {
        String q = "from " + classe.getSimpleName() + " obj where " + hql;
        Query query = em.createQuery(q);
        query.setMaxResults(maxResults);
        List resultList = query.getResultList();
        readVicTenancyFields(resultList);
        return resultList;
    }

    @Transactional(readOnly = true)
    public Single<List<T>> asyncFind(Class classe, String hql, int maxResults) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(find(classe, hql, maxResults));
        });
    }

    @Transactional(readOnly = true)
    public List<T> findFirst10(Class classe, String hql) {
        return find(classe, hql, 10);
    }

    @Transactional(readOnly = true)
    public Single<List<T>> asyncFindFirst10(Class classe, String hql) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(findFirst10(classe, hql));
        });
    }

    public Long count() {
        return repository.count();
    }

    public Single<Long> asyncCount() {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(count());
        });
    }

    public T newEntity() {
        try {
            BaseEntity.useSimpleId = true;
            T newInstance = clazz().newInstance();
            if (newInstance instanceof VicTenancyFieldsBaseEntity) {
                VicTenancyFieldsBaseEntity n = (VicTenancyFieldsBaseEntity) newInstance;
                List<VicField> res = vicFieldRepository.findByHql(new VicQuery("obj.clazz='" + clazz().getCanonicalName() + "'", 0, 1000000, "id"));
                for (VicField vf : res) {
                    if (vf.getFieldType().equals(VicFieldType.DATE)) {
                        n.getVicTenancyFields().put(vf.getName(), new VicFieldValue(vf, newInstance.getId(), VicScriptEngine.evalForDate(vf.getDefaultValueScript(), null)));
                    } else {
                        n.getVicTenancyFields().put(vf.getName(), new VicFieldValue(vf, newInstance.getId(), VicScriptEngine.eval(vf.getDefaultValueScript(), null)));
                    }
                }
            }
            fillCollections(newInstance);
            return newInstance;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Single<T> asyncNewEntity() {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(newEntity());
        });
    }

    public T newEntityForTest() {
        try {
            BaseEntity.useSimpleId = true;
            T newInstance = clazz().newInstance();
            fillCollections(newInstance);
            return newInstance;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Single<T> asyncNewEntityForTest() {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(newEntityForTest());
        });
    }


    @Transactional(readOnly = true)
    public String draw(String id) {
        T entity = repository.findById(id).orElse(null);
        readVicTenancyFields(entity);
        return new DatabaseDiagramBuilder().draw(entity);
    }

    @Transactional(readOnly = true)
    public Single<String> asyncDraw(String id) {
        return Single.create(singleEmitter -> {
            singleEmitter.onSuccess(draw(id));
        });
    }

    @SuppressWarnings("unchecked")
    private Class<T> clazz() {
        return (Class<T>) Utils.inferGenericType(getClass());
    }

    private void saveVicTenancyFields(T resource) {
        VicTenancyFieldsBaseEntity r = (VicTenancyFieldsBaseEntity) resource;
        for (String s : r.getVicTenancyFields().keySet()) {
            VicFieldValue vfv = r.getVicTenancyFields().get(s);
            vfv.setEntityId(r.getId());
            vicFieldValueRepository.save(vfv);
        }
    }

    private void deleteVicTenancyFields(T resource) {
        VicTenancyFieldsBaseEntity r = (VicTenancyFieldsBaseEntity) resource;
        for (String s : r.getVicTenancyFields().keySet()) {
            VicFieldValue vfv = r.getVicTenancyFields().get(s);
            vicFieldValueRepository.delete(vfv);
        }
    }

    private void readVicTenancyFields(List<T> resources) {
        for (T r : resources) {
            readVicTenancyFields(r);
        }
    }

    private void readVicTenancyFields(T resource) {
        if (!(resource instanceof VicTenancyFieldsBaseEntity)) {
            return;
        }
        VicTenancyFieldsBaseEntity r = (VicTenancyFieldsBaseEntity) resource;
        List<VicFieldValue> res = vicFieldValueRepository.findByHql(new VicQuery("obj.entityId='" + r.getId() + "'", 0, 1000000, "id"));
        r.getVicTenancyFields().clear();
        for (VicFieldValue v : res) {
            r.getVicTenancyFields().put(v.getVicField().getName(), v);
        }
    }

    private void fillCollections(T newinstance) {
        Utils.fillColectionsWithEmpty(newinstance);

    }

}

//88B797E428E850E5494404A5
//88B797E428E850E5494404A5
