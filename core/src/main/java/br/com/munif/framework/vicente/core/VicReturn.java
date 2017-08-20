/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.core;

import java.util.List;

/**
 *
 * @author munif
 */
public class VicReturn<T> {

    private final List<T> values;

    public VicReturn(List<T> values) {
        this.values = values;
    }

    public List<T> getValues() {
        return values;
    }

}
