package br.com.munif.framework.vicente.core.vquery;

import java.util.ArrayList;

public class ParamList extends ArrayList<Param> {
    public void add(String key, Object value, Class type) {
        this.add(new Param(key, value, type));
    }
}
