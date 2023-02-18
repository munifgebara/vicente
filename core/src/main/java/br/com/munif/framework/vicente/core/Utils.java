package br.com.munif.framework.vicente.core;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
public class Utils {

    public static List<Field> getAllFields(Class c) {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        if (c.getSuperclass() != Object.class) {
            arrayList.addAll(getAllFields(c.getSuperclass()));
        }
        arrayList.addAll(Arrays.asList(c.getDeclaredFields()));
        return arrayList;
    }

    public static String removeNotNumbers(String numero) {
        if (numero == null) {
            return null;
        }
        return numero.replaceAll("[\\D]", "");
    }

    public static void removeNumbersFromAtrributes(Object obj, String... atributos) {
        Class clazz = obj.getClass();
        for (String attr : atributos) {
            try {
                Field att = clazz.getDeclaredField(attr);
                att.setAccessible(true);
                att.set(obj, removeNotNumbers((String) att.get(obj)));
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
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

    public static String firstTiny(String s) {
        String first = s.substring(0, 1).toLowerCase();
        return first + s.substring(1);

    }

    public static String windowsSafe(String s) {
        return s.replaceAll("\\\\", "/");
    }

    public static List<Class> scanClasses() throws IOException {
        List<Class> toReturn = new ArrayList<>();
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("");
        while (resources.hasMoreElements()) {
            File file = new File(resources.nextElement().getFile());
            toReturn.addAll(scanFolder(file, file));
        }
        return toReturn;
    }

    private static String getClassName(File f, File baseFolder) {
        String compleName = Utils.windowsSafe(f.getAbsolutePath());
        String baseFolderName = Utils.windowsSafe(baseFolder.getAbsolutePath());
        return compleName.replaceFirst(baseFolderName, "").replace(".class", "").replaceAll("/", ".").replaceFirst(".", "");
    }

    public static List<Class> scanFolder(File folder, File baseFolder) {
        List<Class> toReturn = new ArrayList<>();
        File[] fs = folder.listFiles();
        if (fs == null) {
            return toReturn;
        }
        for (File f : fs) {
            if (f.isDirectory()) {
                toReturn.addAll(scanFolder(f, baseFolder));
            } else {
                String name = f.getName();
                if (name.endsWith(".class")) {
                    String className = getClassName(f, baseFolder);
                    try {
                        toReturn.add(Class.forName(className));
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return toReturn;
    }

    public static void fillColectionsWithEmpty(Object obj) {
        try {
            List<Field> fields = getAllFields(obj.getClass());
            for (Field f : fields) {
                if (List.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    if (f.get(obj) == null) {
                        f.set(obj, Collections.EMPTY_LIST);
                    }
                } else if (Set.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    if (f.get(obj) == null) {
                        f.set(obj, Collections.EMPTY_SET);
                    }
                } else if (Map.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    if (f.get(obj) == null) {
                        f.set(obj, Collections.EMPTY_MAP);
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @return null safe set
     */
    public static Set<Field> findFields(Class<?> classs, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

    public static String firstCapital(String s) {
        String first = s.substring(0, 1).toUpperCase();
        return first + s.substring(1);
    }

}
