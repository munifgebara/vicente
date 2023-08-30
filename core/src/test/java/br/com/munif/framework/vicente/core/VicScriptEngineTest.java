/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.core;

import org.junit.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
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
    public void testEvalDate() {
        String script = ""
                + "(function (){" +
                " return  new Date();"
                + "  "
                + "})()";
        Date date = VicScriptEngine.evalForDate(script, Collections.emptyMap());
        assertEquals( new Date().toString(), date.toString());
    }


    @Test
    public void testEval() {
        String script = ""
                + "function dobro(a){"
                + "return 2*a;"
                + "}"
                + ""
                + "(function (){"
                + "   let vv=JSON.parse(v); "
                + "   console.log(JSON.stringify(vv)); "
                + "  return dobro(vv.x+vv.y);"
                + "})()";
        Map<String, Object> objects = new HashMap<>();
        objects.put("v", new V(10,20).asJson());
        Object expResult = 60.0;
        Object result = VicScriptEngine.eval(script, objects);
        result = result instanceof Integer ? Double.valueOf((Integer) result) : result;
        assertEquals(expResult, result);
    }

    class V {
        public double x;
        public double y;


        public V(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String asJson(){
            return "{\"x\":"+x+",\"y\":"+y+"}";
        }
    }



}