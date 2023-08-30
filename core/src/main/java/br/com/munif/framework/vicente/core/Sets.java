package br.com.munif.framework.vicente.core;

import java.util.Arrays;
import java.util.HashSet;

public class Sets {
    public static <E> HashSet<E> newHashSet(E... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }
}
