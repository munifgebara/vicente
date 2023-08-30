/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.core;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author munif
 */
public class UtilsTest {

    public UtilsTest() {
    }

    @Test
    public void testGetAllFields() {
        List<Field> allFields = Utils.getAllFields(Aluno.class);
        assertEquals(3, allFields.size());
    }

    @Test
    public void testRemoveNaoNumeros() {
        assertEquals("12", Utils.removeNotNumbers("A1B2D"));
    }

    @Test
    public void testRemoveNumerosDosAtributos() {
        assert (true);

    }

    @Test
    public void testInferGenericType_Class() {
        Class<?> inferGenericType = Utils.inferGenericType(Aluno.class);
        assertEquals(Long.class, inferGenericType);

    }

    @Test
    public void testInferGenericType_Class_int() {
        Class<?> inferGenericType = Utils.inferGenericType(Aluno.class, 1);
        assertEquals(String.class, inferGenericType);

    }


}
