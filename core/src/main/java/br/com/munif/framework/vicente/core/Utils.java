package br.com.munif.framework.vicente.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static List<Field> getAllFields(Class c) {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        if (c.getSuperclass() != Object.class) {
            arrayList.addAll(getAllFields(c.getSuperclass()));
        }
        arrayList.addAll(Arrays.asList(c.getDeclaredFields()));
        return arrayList;
    }

    public static String removeNaoNumeros(String numero) {
        if (numero == null) {
            return null;
        }
        return numero.replaceAll("[\\D]", "");
    }

    public static void removeNumerosDosAtributosa(Object obj, String... atributos) {
        Class clazz = obj.getClass();
        for (String atributo : atributos) {
            try {
                Field att = clazz.getDeclaredField(atributo);
                att.setAccessible(true);
                att.set(obj, removeNaoNumeros((String) att.get(obj)));
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static Class<?> inferGenericType(Class<?> clazz) {
        return inferGenericType(clazz, 0);
    }

    public static Class<?> inferGenericType(Class<?> clazz, int index) {
        Type superClass = clazz.getGenericSuperclass();
        return (Class<?>) ((ParameterizedType) superClass).getActualTypeArguments()[index];
    }

    public static String primeiraMinuscula(String s) {
        String first = s.substring(0, 1).toLowerCase();
        return first + s.substring(1);

    }

    public static void main(String args[]) {
        
        System.out.println(primeiraMinuscula("ABC"));

    }

}
