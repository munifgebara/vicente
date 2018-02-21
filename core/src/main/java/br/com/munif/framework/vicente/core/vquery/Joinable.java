package br.com.munif.framework.vicente.core.vquery;

public interface Joinable<T> {
    public T on(Criteria criteria);
}
