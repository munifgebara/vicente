package br.com.munif.framework.vicente.core.vquery;

import org.apache.commons.lang3.RandomStringUtils;

public class Param {
    private String key;
    private Object value;
    private String type;

    public Param() {
    }

    public Param(String key, Object value, String type) {
        onInit(key, value, type);
    }

    private void onInit(String key, Object value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public Param(Object value, String type) {
        onInit(generateParamId(), value, type);
    }

    public static String generateParamId() {
        return ":" + RandomStringUtils.randomAlphabetic(10).toLowerCase().concat(RandomStringUtils.randomNumeric(10));
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

    public String getKeyToSearch() {
        return key.replace(":", "");
    }

    public Object getValueToSearch() {
        if ("String".equals(type)) return ((String) value).substring(1, ((String) value).length() - 1);
        if ("Integer".equals(type)) value = Integer.valueOf(String.valueOf(value));
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Param setBuilderValue(Object value) {
        this.value = value;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
