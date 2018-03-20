package br.com.munif.framework.vicente.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VicAutoSeed {

    //private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final Logger LOG = LoggerFactory.getLogger(VicAutoSeed.class);

    private static final Random random = new Random(1);

    public static BigDecimal getRandomBigDecimal(double min, double max) {
        return new BigDecimal(min + random.nextDouble() * (max - min));
    }

    public static Integer getRandomInteger(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static String getRandomString(int tamanho) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder s = new StringBuilder();
        while (s.length() < tamanho) { // length of the random string.
            int index = (int) (random.nextDouble() * SALTCHARS.length());
            s.append(SALTCHARS.charAt(index));
        }
        String saltStr = s.toString();
        return saltStr;

    }

    public static Object[] getDataMuseWord(String s) {
        ObjectMapper om = new ObjectMapper();
        try {
            URL url = new URL("http://api.datamuse.com/words?" + s);
            List<Map> readValue = om.readValue(url, List.class);
            Object[] result = readValue.stream().map(a -> a.get("word")).toArray();
            return result;
        } catch (Exception e) {
            LOG.info("Problem in getDataMuseWord Query " + s, e);
        }

        return new Object[0];

    }

    public static void randomFill(Object obj) {
        try {

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(Version.class)) {
                    continue;
                }
                if (!Modifier.isStatic(f.getModifiers())) {
                    f.setAccessible(true);
                    if (f.get(obj) == null) {
                        Class<?> type = f.getType();
                        if (type.equals(String.class)) {
                            f.set(obj, getRandomString(20));
                        } else if (type.equals(Integer.class)) {
                            f.set(obj, getRandomInteger(100, 1000));
                        } else if (type.equals(BigDecimal.class)) {
                            f.set(obj, getRandomBigDecimal(10, 100));
                        } else {
                            LOG.info("No random generator for " + type + " int class " + obj.getClass().getSimpleName() + " " + f.getName());
                        }
                    }
                }
            }

        } catch (Exception ex) {
            LOG.error("randomFill "+obj.getClass().getSimpleName(), ex);
        }
    }

    public static List getInteligentInstances(Object exaple, int number) {
        Class<?> clazz = exaple.getClass();
        Map<Field, Object[]> searched = new HashMap<>();
        List toReturn = new ArrayList();
        for (int i = 0; i < number; i++) {
            try {
                Field[] declaredFields = exaple.getClass().getDeclaredFields();
                Object newInstance = clazz.newInstance();
                for (Field f : declaredFields) {
                    if (f.isAnnotationPresent(Version.class)) {
                        continue;
                    }
                    if (!Modifier.isStatic(f.getModifiers())) {
                        f.setAccessible(true);
                        if (f.get(exaple) == null) {
                            Class<?> type = f.getType();
                            if (type.equals(String.class)) {
                                if (!searched.containsKey(f)) {
                                    searched.put(f, getDataMuseWord("ml="+translates(clazz.getSimpleName() + "+" + f.getName()) + "&max=" + (number+3)));
                                }
                                Object[] values = searched.get(f);
                                if (values.length > 0) {
                                    f.set(newInstance, values[(i+3) % values.length]);
                                } else {
                                    f.set(newInstance, getRandomString(20));
                                }
                            } else if (type.equals(Integer.class)) {
                                f.set(newInstance, getRandomInteger(100, 1000));
                            } else if (type.equals(BigDecimal.class)) {
                                f.set(newInstance, getRandomBigDecimal(10, 100));
                            } else {
                                LOG.info("No random generator for " + type + " int class " + translates(exaple.getClass().getSimpleName()) + " " + translates(f.getName()));
                            }
                        } else {
                            f.set(newInstance, f.get(exaple));
                        }
                    }
                }
                toReturn.add(newInstance);
            } catch (Exception ex) {
                LOG.info("problem in getInteligentInstances" + exaple.getClass().getSimpleName(), ex);
                
            }
        }

        return toReturn;
    }

    public static List<List> subLists(int tamanhoMinimo, int tamanhoMaximo, Collection collection) {
        List<Object[]> subLists = new ArrayList<>();
        Object[] input = collection.toArray();

        for (int k = tamanhoMinimo; k <= tamanhoMaximo; k++) {

            int[] s = new int[k];                  // here we'll keep indices 
            // pointing to elements in input array

            if (k <= input.length) {
                // first index sequence: 0, 1, 2, ...
                for (int i = 0; (s[i] = i) < k - 1; i++);
                subLists.add(getSubList(input, s));
                for (;;) {
                    int i;
                    // find position of item that can be incremented
                    for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--);
                    if (i < 0) {
                        break;
                    }
                    s[i]++;                    // increment this item
                    for (++i; i < k; i++) {    // fill up remaining items
                        s[i] = s[i - 1] + 1;
                    }
                    subLists.add(getSubList(input, s));
                }
            }
        }
        List<List> toReturn = new ArrayList<List>();
        for (Object[] sub : subLists) {
            toReturn.add(new ArrayList(Arrays.asList(sub)));
        }
        return toReturn;
    }

    private static Object[] getSubList(Object[] input, int[] subset) {
        Object[] result = new Object[subset.length];
        for (int i = 0; i < subset.length; i++) {
            result[i] = input[subset[i]];
        }
        return result;
    }

    public static void main(String args[]) {

    }

    private static String translates(String p) {
        for (int i = 0; i < DICTIONARY.length; i += 2) {
            if (p.equalsIgnoreCase(DICTIONARY[i])) {
                return DICTIONARY[i + 1];
            }
        }
        return p;

    }

    public static String DICTIONARY[] = {
        "Categoria+nome", "product+type",
        "Produto+nome", "product+name",
        "Cliente+nome", "customer",
        "Cliente+cidade", "city"
    };

}
