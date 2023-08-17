package br.com.munif.framework.vicente.domain;

public interface IBaseEntity {
    boolean canRead();

    boolean canUpdate();

    boolean canDelete();

    void setId(String id);

    String getId();

    String r();
}
