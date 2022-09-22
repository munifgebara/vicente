package br.com.munif.framework.vicente.core.vquery;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class Param {
    private String key;
    private String field;
    private Object value;
    private Class type;

    public Param() {
    }

    public Param(String key, Object value, Class type) {
        onInit(key, value, type);
    }

    public Param(String key, Object value) {
        onInit(key, value, value.getClass());
    }

    public Param(Object value, Class type) {
        onInit(generateParamId(), value, type);
    }

    public Param(String key, Class<?> aClass, Object field) {
        onInit(generateParamId(), value, type);
        this.field = String.valueOf(field);
    }

    public static String generateParamId() {
        return ":" + RandomStringUtils.randomAlphabetic(10).toLowerCase().concat(RandomStringUtils.randomNumeric(10));
    }

    private void onInit(String key, Object value, Class type) {
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

    public void setValue(Object value) {
        this.value = value;
    }

    public String getKeyToSearch() {
        return key.replace(":", "");
    }

    public Object getValueToSearch() {
        if (value == null) return null;
        if (String.class.equals(getType())) return ((String) value).substring(1, ((String) value).length() - 1);
        if (Integer.class.equals(getType())) value = Integer.valueOf(String.valueOf(value));
        if (LocalDate.class.equals(getType()))
            value = LocalDate.parse(String.valueOf(value).replace("'", "").split("T")[0]);
        if (ZonedDateTime.class.equals(getType())) value = ZonedDateTime.parse(String.valueOf(value).replace("'", ""));
        if (LinkedHashMap.class.equals(getType()) && value != null) {
            LinkedHashMap map = ((LinkedHashMap) value);
            if (map.containsKey("field")) {
                value = String.valueOf(map.get("field"));
            }
        }
        if (getType().isEnum()) value = Enum.valueOf(getType(), String.valueOf(value).replace("'", ""));
        return value;
    }

    public Param setBuilderValue(Object value) {
        this.value = value;
        return this;
    }

    public Class getType() {
        return type != null ? type : value != null ? value.getClass() : null;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
