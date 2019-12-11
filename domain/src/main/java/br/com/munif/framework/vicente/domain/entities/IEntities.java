package br.com.munif.framework.vicente.domain.entities;

import java.io.Writer;
import java.util.List;

public interface IEntities {
    List<String> createClass(Class entity, Writer fw) throws Exception;
}
