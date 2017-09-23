/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test;

import br.com.munif.framework.vicente.api.VicAutoSeed;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author munif
 */
public class VicAutoSeedTest {

    public VicAutoSeedTest() {
    }

    @org.junit.Test
    public void testGetRandomBigDecimal() {

        for (int i = 0; i < 100; i++) {
            BigDecimal randomBigDecimal = VicAutoSeed.getRandomBigDecimal(0, 10);
            assertTrue(randomBigDecimal.compareTo(BigDecimal.ZERO) >= 0 && randomBigDecimal.compareTo(BigDecimal.TEN) < 0);
        }
    }

    @org.junit.Test
    public void testGetRandomInteger() {
        for (int i = 0; i < 100; i++) {
            Integer randomInteger = VicAutoSeed.getRandomInteger(10, 100);
            assertTrue(randomInteger >= 10 && randomInteger < 100);
        }
    }

    @org.junit.Test
    public void testGetRandomString() {
        for (int i = 0; i < 100; i++) {
            String randomString = VicAutoSeed.getRandomString(i);
            assertTrue(randomString.length() == i);
        }
    }

    @org.junit.Test
    public void testGetDataMuseWord() {
        Object[] dataMuseWord = VicAutoSeed.getDataMuseWord("ml=car+brand");
        assertTrue(dataMuseWord.length > 0);
    }

    @org.junit.Test
    public void testRandomFill() {
        Carro c = new Carro();
        c.setModelo("%meuModelo%");
        VicAutoSeed.randomFill(c);
        assertEquals("%meuModelo%", c.getModelo());
        assertNotNull(c.getAnoFabricacao());
        assertNotNull(c.getAnoModelo());
        assertNotNull(c.getPlaca());
        assertNotNull(c.getMarca());
    }

    @org.junit.Test
    public void testGetInteligentInstances() {
        Carro c = new Carro();
        c.setModelo("%meuModelo%");
        List<Carro> inteligentInstances = VicAutoSeed.getInteligentInstances(c, 5);
        for (Carro d : inteligentInstances) {
            assertEquals("%meuModelo%", c.getModelo());
            assertNotNull(d.getAnoFabricacao());
            assertNotNull(d.getAnoModelo());
            assertNotNull(d.getPlaca());
            assertNotNull(d.getMarca());
        }
    }

    @org.junit.Test
    public void testSubLists() {
        List<List> subLists = VicAutoSeed.subLists(2, 3, Arrays.asList(new String[]{"A","B","C","D","E"}));
        assertEquals(20, subLists.size());
        assertTrue(subLists.contains(Arrays.asList(new String[]{"B","C"})));
        assertTrue(subLists.contains(Arrays.asList(new String[]{"B","C","D"})));
        assertTrue(subLists.contains(Arrays.asList(new String[]{"A","B"})));
        assertTrue(subLists.contains(Arrays.asList(new String[]{"C","D","E"})));
    }


}
