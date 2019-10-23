package br.com.munif.framework.vicente.core.vquery;

public class Param {
    private String key;
    private Object value;
    private String type;

    public Param(String key, Object value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public Object getValueToSearch() {
        if ("String".equals(type)) return ((String) value).substring(1, ((String) value).length() - 1);
        if ("Integer".equals(type)) value = Integer.valueOf(String.valueOf(value));
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
