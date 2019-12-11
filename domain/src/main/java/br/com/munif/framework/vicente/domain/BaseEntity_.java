/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;


import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * @author munif
 */
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

    public static volatile SingularAttribute<BaseEntity, String> id;
    public static volatile SingularAttribute<BaseEntity, String> oi;
    public static volatile SingularAttribute<BaseEntity, String> gi;
    public static volatile SingularAttribute<BaseEntity, String> ui;

    public static volatile SingularAttribute<BaseEntity, String> rights;
    public static volatile SingularAttribute<BaseEntity, String> extra;
    public static volatile SingularAttribute<BaseEntity, Date> cd;
    public static volatile SingularAttribute<BaseEntity, Date> ud;
    public static volatile SingularAttribute<BaseEntity, Boolean> active;

    public static volatile SingularAttribute<BaseEntity, Integer> version;

}
