/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.application.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.transform.ResultTransformer;

/**
 *
 * @author munif
 */
public class VicResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] os, String[] strings) {
        Map<String,Object> r=new HashMap<>();
        for (int i=0;i<os.length;i++){
            r.put(strings[i]!=null?strings[i]:"field "+i, os[i]);
        }
        return r;
    }

    @Override
    public List transformList(List list) {
        return list;
    }

}
