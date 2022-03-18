package br.com.munif.framework.vicente.core.vquery;

/**
 * @author wmfsystem
 */
public interface Joinable<T> {
    T on(Criteria criteria);
}
