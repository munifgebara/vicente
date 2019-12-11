package br.com.munif.framework.vicente.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {
    /**
     * Map the attributtes values from object
     *
     * @param fields selected fields
     * @param obj    object with the values
     */
    public static Map<String, Object> objectFieldsToMap(String[] fields, Object obj) {
        Map<String, Object> row = new HashMap<>();
        for (String f : fields) {
            try {
                Field field = ReflectionUtil.getField(obj.getClass(), f.trim());
                field.setAccessible(true);
                row.put(f.trim(), field.get(obj));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return row;
    }

    /**
     * Search recursively field in the class
     *
     * @param clazz class to search
     * @param name  attribute name
     * @return found attribute or null
     */
    public static Field getField(Class clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException ignored) {
        }
        if (!Object.class.equals(clazz.getSuperclass())) {
            return getField(clazz.getSuperclass(), name);
        }

        return null;
    }

}
