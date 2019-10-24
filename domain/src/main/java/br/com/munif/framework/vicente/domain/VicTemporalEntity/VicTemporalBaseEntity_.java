package br.com.munif.framework.vicente.domain.VicTemporalEntity;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.BaseEntity_;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author munif
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VicTemporalBaseEntity.class)
public abstract class VicTemporalBaseEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity, Long> startTime;
    public static volatile SingularAttribute<BaseEntity, Long> endTime;

}
