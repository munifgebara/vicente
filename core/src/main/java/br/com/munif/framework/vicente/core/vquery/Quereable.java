package br.com.munif.framework.vicente.core.vquery;

import java.util.List;

public interface Quereable<T> {
    T or(Criteria criteria);
    T and(Criteria criteria);
    T or(T criteria);
    T and(T criteria);

    default String getAlias() {
        return "";
    }

    default Boolean getUseDistinct() {
        return false;
    }

    default void addIgnoreCase() {}

    default List<Quereable> getSubQuerys() {
        return null;
    }
}
