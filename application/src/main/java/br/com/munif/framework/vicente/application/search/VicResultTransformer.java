/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application.search;

import br.com.munif.framework.vicente.core.Utils;
import org.hibernate.transform.ResultTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author munif
 */
public class VicResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] os, String[] strings) {
        Map<String, Object> r = new HashMap<>();
        Map<String, Integer> counters = new HashMap<>();
        for (int i = 0; i < os.length; i++) {
            String fieldName = "field" + i;
            if (strings == null || strings[i] == null) {
                String className = os[i].getClass().getSimpleName();
                if (!counters.containsKey(className)) {
                    counters.put(className, 0);
                }
                int v = counters.get(className);
                fieldName = Utils.firstTiny(className) + (v > 0 ? v : "");
                v++;
                counters.put(className, v);
            } else {
                fieldName = strings[i];
            }
            r.put(fieldName, os[i]);
        }
        return r;
    }

    @Override
    public List transformList(List list) {
        return list;
    }

}
