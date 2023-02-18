//package br.com.munif.framework.vicente.application;
//
//import br.com.munif.framework.vicente.core.*;
//import br.com.munif.framework.vicente.core.phonetics.PhoneticBuilder;
//import br.com.munif.framework.vicente.core.phonetics.VicPhoneticPolicy;
//import br.com.munif.framework.vicente.core.vquery.*;
//import br.com.munif.framework.vicente.domain.BaseEntity;
//import br.com.munif.framework.vicente.domain.VicTemporalEntity.VicTemporalBaseEntity;
//import br.com.munif.framework.vicente.domain.VicTemporalEntity.VicTemporalBaseEntityHelper;
//import org.hibernate.transform.ResultTransformer;
//import org.springframework.data.r2dbc.convert.R2dbcConverter;
//import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
//import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
//import org.springframework.data.relational.core.query.Query;
//import org.springframework.data.relational.repository.query.RelationalEntityInformation;
//import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.util.ReflectionUtils;
//import reactor.core.publisher.Flux;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Parameter;
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @param <T> Entity that extends BaseEntity
// * @author munif
// */
//@NoRepositoryBean
//public class VicReactiveRepositoryImpl<T extends BaseEntity> extends SimpleR2dbcRepository<T, Serializable> implements VicReactiveRepository<T> {
//
//    private final EntityManager entityManager;
//
//    private final RelationalEntityInformation<T, Serializable> entity;
//    private final R2dbcEntityOperations entityOperations;
//
//
//    public VicReactiveRepositoryImpl(RelationalEntityInformation<T, Serializable> entity, R2dbcEntityOperations entityOperations, R2dbcConverter converter, EntityManager entityManager) {
//        super(entity, entityOperations, converter);
//        entityOperations.select()
//        this.entity = entity;
//        this.entityOperations = entityOperations;
//        this.entityManager = entityManager;
//    }
//
//    protected Class<T> getDomainClass() {
//        return this.entity.getJavaType();
//    }
//
//    /**
//     * Search elements that
//     * (data have the user id and the user rights) or
//     * (data contains the group id and the group rights) or
//     * (data contains the organization id) or
//     * (data is public) or
//     * (data is ative in the current time if the domain is a VicTemporalEntity)
//     *
//     * @param publics includes public elements
//     * @return hql with tenancy
//     */
//    public String geTenancyHQL(boolean publics, String alias) {
//        Class<T> domainClass = this.getDomainClass();
//        boolean isVicTemporalEntity = VicTemporalBaseEntity.class.isAssignableFrom(domainClass) && (!Boolean.TRUE.equals(VicThreadScope.ignoreTime.get()));
//        boolean assignableFrom2 = BaseEntity.class.isAssignableFrom(domainClass);
//        if (!assignableFrom2) {
//            return "(1=1)";
//        }
//        VicTenancyType vtt = getPolicy(domainClass);
//        StringBuilder sb = new StringBuilder("(\n");
//        if (isVicTemporalEntity) {
//            sb.append("(\n");
//        }
//        if (vtt.equals(VicTenancyType.ONLY_HIERARCHICAL_TOP_DOWN)) {
//            sb.append(" (").append(alias).append(".oi like :oi)\n ");
//        } else {
//            sb.append("   (").append(alias).append(".ui=:ui and mod(").append(alias).append(".rights/64,8)/4>=1) \n");
//
//            if (vtt.equals(VicTenancyType.GROUPS) || vtt.equals(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN) || vtt.equals(VicTenancyType.GROUPS_AND_ORGANIZATIONAL)) {
//                sb.append("or (:gi like concat('%',").append(alias).append(".gi,',%') and mod(").append(alias).append(".rights/8,8)/4>=1) \n");
//            }
//
//            if (vtt.equals(VicTenancyType.HIERARCHICAL_TOP_DOWN) || vtt.equals(VicTenancyType.ORGANIZATIONAL)) {
//                sb.append("or (").append(alias).append(".oi like :oi)\n");
//            } else if (vtt.equals(VicTenancyType.GROUPS_AND_HIERARCHICAL_TOP_DOWN) || vtt.equals(VicTenancyType.GROUPS_AND_ORGANIZATIONAL)) {
//                sb.append("and (").append(alias).append(".oi like :oi)\n");
//            }
//        }
//        if (publics) {
//            sb.append("or (mod(").append(alias).append(".rights  ,8)/4>=1)\n");
//        }
//        if (isVicTemporalEntity) {
//            sb.append(") and (").append(alias).append(".startTime<=:et and :et<=").append(alias).append(".endTime) \n");
//        }
//        sb.append(") and ").append(alias).append(".active is true");
//
//        return sb.toString();
//    }
//
//    /**
//     * Get the type of tenancy of the domain
//     *
//     * @param domainClass class that contains the VicTenancyType Annotation
//     * @return tenancy policy
//     */
//    public VicTenancyType getPolicy(Class<T> domainClass) {
//        VicTenancyPolicy vtp = domainClass.getAnnotation(VicTenancyPolicy.class);
//        return vtp == null ? VicTenancyType.GROUPS : vtp.value();
//    }
//
//    public void setTenancyParameters(Query query) {
//        Set<Parameter<?>> parameters = query.getParameters();
//        for (Parameter p : parameters) {
//            if ("et".equals(p.getName())) {
//                query.setParameter("et", VicTemporalBaseEntityHelper.getEffectiveTime());
//            }
//            if ("ui".equals(p.getName())) {
//                query.setParameter("ui", VicThreadScope.ui.get());
//            }
//            if ("gi".equals(p.getName())) {
//                query.setParameter("gi", VicThreadScope.gi.get());
//            }
//            if ("oi".equals(p.getName())) {
//                VicTenancyType vtt = getPolicy(getDomainClass());
//                String oi = VicThreadScope.oi.get();
//                if (oi == null) {
//                    oi = ".";
//                }
//                if (vtt.equals(VicTenancyType.ORGANIZATIONAL) || vtt.equals(VicTenancyType.GROUPS_AND_ORGANIZATIONAL)) {
//                    query.setParameter("oi", oi.substring(0, oi.indexOf('.') + 1) + "%");
//                } else {
//                    query.setParameter("oi", oi + "%");
//                }
//            }
//
//        }
//    }
//
//    /**
//     * Find all elements without tenancy filter
//     *
//     * @return all domain elements
//     */
//    public Flux<T> findAllNoTenancy() {
//        return super.findAll();
//    }
//
//    /**
//     * Find all elements according tenancy including public elements
//     *
//     * @return all domain elements by tenancy
//     */
//    @Override
//    public Flux<T> findAll() {
//        String hql = "FROM " + getDomainClass().getSimpleName() + " obj where \n" + geTenancyHQL(true, "obj");
//
//        Query query = new Query();
//        session
//        setTenancyParameters(query);
//
//        entityOperations.select(query, this.entity.getJavaType());
//
//        return query.getResultList();
//    }
//
//    /**
//     * Find all elements according tenancy without public elements
//     *
//     * @return all domain elements by tenancy
//     */
//    public List<T> findAllNoPublic() {
//        String hql = "FROM " + getDomainClass().getSimpleName() + " obj where \n" + geTenancyHQL(false, VicRepositoryUtil.DEFAULT_ALIAS);
//        Query query = entityManager.createQuery(hql);
//
//        setTenancyParameters(query);
//        return query.getResultList();
//    }
//
//    /**
//     * Find elements according using the VicQuery
//     *
//     * @param vicQuery VicQuery
//     * @return domain elements according query
//     */
//    @Override
//    public List<T> findByHql(VicQuery vicQuery) {
//        return getQuery(vicQuery, getDomainClass()).getResultList();
//    }
//
//    @Override
//    public Query getQuery(VicQuery vicQuery, Class clazz) {
//        if (vicQuery.getEntity() == null) vicQuery.setEntity(getDomainClass().getSimpleName());
//        if (vicQuery.getMaxResults() == -1) {
//            vicQuery.setMaxResults(VicQuery.DEFAULT_QUERY_SIZE);
//        }
//
//        String clause = (vicQuery.getHql() != null ? vicQuery.getHql() : "1=1")
//                .concat(" and ")
//                .concat((vicQuery.getQuery() != null ? vicQuery.getQuery().toString() : "1=1"));
//
//        String joins = "";
//        String attrs = VicRepositoryUtil.DEFAULT_ALIAS;
//        String alias = VicRepositoryUtil.DEFAULT_ALIAS;
//        ParamList params = null;
//        if (vicQuery.getQuery() != null) {
//            attrs = vicQuery.getQuery().getFieldsWithAlias();
//            joins = vicQuery.getQuery().getJoins();
//            alias = vicQuery.getQuery().getAlias();
//            params = vicQuery.getQuery().getParams();
//        }
//
//        String hql = mountSelectWithWhere(vicQuery, clause, joins, attrs, alias, true);
//        Query query = entityManager.createQuery(hql, clazz);
//        query.setFirstResult(vicQuery.getFirstResult());
//        query.setMaxResults(vicQuery.getMaxResults());
//        setTenancyParameters(query);
//        if (params != null) {
//            for (Param entry : params) {
//                try {
//                    query.setParameter(entry.getKeyToSearch(), entry.getValueToSearch());
//                } catch (IllegalArgumentException ex) {
//                    String field = entry.getField().replace(alias + ".", "");
//                    try {
//                        entry.setType(getDomainClass().getDeclaredField(field).getType());
//                        query.setParameter(entry.getKeyToSearch(), entry.getValueToSearch());
//                    } catch (NoSuchFieldException e) {
//                        hql = hql.replace(entry.getKey(), String.valueOf(entry.getValue()));
//                        Query newQuery = entityManager.createQuery(hql, clazz);
//                        newQuery.setFirstResult(vicQuery.getFirstResult());
//                        newQuery.setMaxResults(vicQuery.getMaxResults());
//                        for (Parameter<?> parameter : query.getParameters()) {
//                            Object parameterValue = query.getParameterValue(parameter);
//                            if (parameterValue != null)
//                                newQuery.setParameter(parameter.getName(), parameterValue);
//                        }
//                        query = newQuery;
//                    }
//                }
//            }
//        }
//        if (!VicRepositoryUtil.DEFAULT_ALIAS.equals(attrs)) {
//            query = selectAttributes(query);
//        }
//        return query;
//    }
//
//    /**
//     * Find elements according using the VicQuery without tenancy
//     *
//     * @param vicQuery VicQuery
//     * @return domain elements according query
//     */
//    @Override
//    public List<T> findByHqlNoTenancy(VicQuery vicQuery) {
//        if (vicQuery.getMaxResults() == -1) {
//            vicQuery.setMaxResults(VicQuery.DEFAULT_QUERY_SIZE);
//        }
//
//        String clause = (vicQuery.getHql() != null ? vicQuery.getHql() : "1=1")
//                .concat(" and ")
//                .concat((vicQuery.getQuery() != null ? vicQuery.getQuery().toString() : "1=1"));
//
//        String joins = "";
//        String attrs = VicRepositoryUtil.DEFAULT_ALIAS;
//        String alias = VicRepositoryUtil.DEFAULT_ALIAS;
//        ParamList params = null;
//        if (vicQuery.getQuery() != null) {
//            attrs = vicQuery.getQuery().getFieldsWithAlias();
//            joins = vicQuery.getQuery().getJoins();
//            alias = vicQuery.getQuery().getAlias();
//            params = vicQuery.getQuery().getParams();
//        }
//
//        String hql = mountSelectWithWhere(vicQuery, clause, joins, attrs, alias, false);
//        Query query = entityManager.createQuery(hql);
//        query.setFirstResult(vicQuery.getFirstResult());
//        query.setMaxResults(vicQuery.getMaxResults());
//        if (params != null) {
//            for (Param entry : params) {
//                query.setParameter(entry.getKeyToSearch(), entry.getValueToSearch());
//            }
//        }
//        if (!VicRepositoryUtil.DEFAULT_ALIAS.equals(attrs)) {
//            query = selectAttributes(query);
//        }
//        return query.getResultList();
//    }
//
//    private org.hibernate.query.Query selectAttributes(Query query) {
//        return query.unwrap(org.hibernate.query.Query.class)
//                .setResultTransformer(new ResultTransformer() {
//                    @Override
//                    public Object transformTuple(Object[] tuple, String[] aliases) {
//                        T t = null;
//                        try {
//                            t = getDomainClass().newInstance();
//                            Class<? extends BaseEntity> aClass = t.getClass();
//                            for (int i = 0; i < aliases.length; i++) {
//                                Field declaredField = aClass.getDeclaredField(aliases[i]);
//                                declaredField.setAccessible(true);
//                                declaredField.set(t, tuple[i]);
//                            }
//                        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
//                            e.printStackTrace();
//                        }
//                        return t;
//                    }
//
//                    @Override
//                    public List transformList(List collection) {
//                        return collection;
//                    }
//                });
//    }
//
//    private String mountSelectWithWhere(VicQuery vicQuery, String clause, String joins, String attrs, String alias, boolean withTenancy) {
//        return mountSelect(joins, attrs, alias, vicQuery.getEntity()) + mountWhere(vicQuery, clause, joins, attrs, alias, withTenancy, true);
//    }
//
//    private String mountDeleteWithWhere(VicQuery vicQuery, String clause, String joins, String attrs, String alias) {
//        return mountDelete(alias) + mountWhere(vicQuery, clause, joins, attrs, alias, true, false);
//    }
//
//    private String mountSelect(String joins, String attrs, String alias, String entity) {
//        return "select " + attrs + " FROM " + (entity == null ? getDomainClass().getSimpleName() : entity) + " " + alias + " " + joins + " ";
//    }
//
//    private String mountDelete(String alias) {
//        return "delete FROM " + getDomainClass().getSimpleName() + " " + alias + " ";
//    }
//
//    private String mountWhere(VicQuery vicQuery, String clause, String joins, String attrs, String alias, boolean withTenancy, boolean withOrder) {
//        return " where \n"
//                + "(" + clause + ") "
//                + (withTenancy ? " and (" + geTenancyHQL(true, alias) + ") " : "") +
//                (withOrder ? " ORDER BY " + alias + "." + vicQuery.getOrderBy() + " " + vicQuery.getSortDir() : "");
//    }
//
//    @Override
//    public void patch(Map<String, Object> map) {
//        SetUpdateQuery setUpdate = VicRepositoryUtil.getSetUpdate(map);
//        String str = " update " + getDomainClass().getSimpleName() + " " + VicRepositoryUtil.DEFAULT_ALIAS + " set "
//                + setUpdate + " where " + VicRepositoryUtil.DEFAULT_ALIAS + ".id = '" + map.get("id") + "' and "
//                + geTenancyHQL(false, VicRepositoryUtil.DEFAULT_ALIAS);
//        Query query = entityManager.createQuery(str);
//        for (Param param : setUpdate.getParams()) {
//            query.setParameter(param.getKeyToSearch(), param.getValue());
//        }
//        setTenancyParameters(query);
//        query.executeUpdate();
//    }
//
//    @Override
//    public T patchReturning(Map<String, Object> map) {
//        T byId = load(String.valueOf(map.get("id")));
//        try {
//            patchReturningRecursively(map, byId);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return save(byId);
//    }
//
//    private void patchReturningRecursively(Map<String, Object> map, Object t) throws IllegalAccessException {
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            if (entry.getValue() instanceof Map) {
//                Field field = ReflectionUtil.getField(t.getClass(), entry.getKey());
//                field.setAccessible(true);
//                patchReturningRecursively((Map<String, Object>) entry.getValue(), field.get(t));
//            } else {
//                Field field = ReflectionUtil.getField(t.getClass(), entry.getKey());
//                field.setAccessible(true);
//                field.set(t, entry.getValue());
//            }
//        }
//    }
//
//    @Override
//    public <S extends T> S save(S entity) {
//        setPhoneticField(entity);
//        return super.save(entity);
//    }
//
//    private <S extends T> void setPhoneticField(S entity) {
//        Class<T> domainClass = this.getDomainClass();
//        boolean assignableFromBaseEntity = BaseEntity.class.isAssignableFrom(domainClass);
//        if (assignableFromBaseEntity) {
//            VicPhoneticPolicy vicPhoneticPolicy = domainClass.getAnnotation(VicPhoneticPolicy.class);
//            if (vicPhoneticPolicy != null) {
//                String field = vicPhoneticPolicy.field();
//                try {
//                    Field declaredField = domainClass.getDeclaredField(field);
//                    declaredField.setAccessible(true);
//                    Object declaredFieldValue = declaredField.get(entity);
//                    Field phonetic = ReflectionUtils.findField(domainClass, vicPhoneticPolicy.phoneticField());
//                    phonetic.setAccessible(true);
//                    String translatedValue = PhoneticBuilder.build().translate(String.valueOf(declaredFieldValue));
//                    phonetic.set(entity, translatedValue);
//
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @Override
//    public T load(String id) {
//        VicQuery vicQuery = new VicQuery(new VQuery(new Criteria("id", ComparisonOperator.EQUAL, id)), 1);
//        List<T> byHql = findByHql(vicQuery);
//        return byHql.size() > 0 ? byHql.get(0) : null;
//    }
//
//    @Override
//    public T loadNoTenancy(String id) {
//        return findById(id).orElse(null);
//    }
//
//    @Override
//    public Boolean isNew(String id) {
//        Query query = entityManager.createQuery("select count(obj) from " + getDomainClass().getSimpleName() + " obj where obj.id = :id");
//        query.setParameter("id", id);
//        Long singleResult = (Long) query.getSingleResult();
//        return singleResult <= 0;
//    }
//
//    @Override
//    public void deleteByHQL(VicQuery vicQuery) {
//        if (vicQuery.getMaxResults() == -1) {
//            vicQuery.setMaxResults(VicQuery.DEFAULT_QUERY_SIZE);
//        }
//
//        String clause = (vicQuery.getHql() != null ? vicQuery.getHql() : "1=1")
//                .concat(" and ")
//                .concat((vicQuery.getQuery() != null ? vicQuery.getQuery().toString() : "1=1"));
//
//        String joins = "";
//        String attrs = VicRepositoryUtil.DEFAULT_ALIAS;
//        String alias = VicRepositoryUtil.DEFAULT_ALIAS;
//        ParamList params = null;
//        if (vicQuery.getQuery() != null) {
//            attrs = vicQuery.getQuery().getFieldsWithAlias();
//            joins = vicQuery.getQuery().getJoins();
//            alias = vicQuery.getQuery().getAlias();
//            params = vicQuery.getQuery().getParams();
//        }
//
//        String hql = mountDeleteWithWhere(vicQuery, clause, joins, attrs, alias);
//        Query query = entityManager.createQuery(hql);
//        query.setFirstResult(vicQuery.getFirstResult());
//        query.setMaxResults(vicQuery.getMaxResults());
//        setTenancyParameters(query);
//        if (params != null) {
//            for (Param entry : params) {
//                query.setParameter(entry.getKeyToSearch(), entry.getValueToSearch());
//            }
//        }
//        if (!VicRepositoryUtil.DEFAULT_ALIAS.equals(attrs)) {
//            query = selectAttributes(query);
//        }
//        query.executeUpdate();
//    }
//}
