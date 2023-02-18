/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.core;

import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author munif
 */
public class VicScriptEngineTest {

    public VicScriptEngineTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of eval method, of class VicScriptEngine.
     */
    @Test
    public void testEval() {
        Map<String, Double> valores = new HashMap<>();
        valores.put("x", 10.0);
        valores.put("y", 20.0);

        String script = ""
                + "function dobro(a){"
                + "return 2*a;"
                + "}"
                + ""
                + "(function (){"
                + "  if (v.get('x')>v.get('y')) return 15; else return dobro(15);"
                + "})()";
        Map<String, Object> objects = new HashMap<>();
        objects.put("v", valores);
        Object expResult = 30.0;
        Object result = VicScriptEngine.eval(script, objects);
        result = result instanceof Integer ? Double.valueOf((Integer) result) : result;
        assertEquals(expResult, result);
    }


}
