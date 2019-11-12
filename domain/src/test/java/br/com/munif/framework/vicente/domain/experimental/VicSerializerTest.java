/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.experimental;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author munif
 */
public class VicSerializerTest {

    public VicSerializerTest() {
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
     * Test of getInstance method, of class VicSerializer.
     */
    @Test
    public void testGetInstance() {
        VicSerializer expResult = VicSerializer.getInstance();
        VicSerializer result = VicSerializer.getInstance();
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of serialize method, of class VicSerializer.
     */
    @Test
    public void testSerialize_Object() {
        VicSerializer.setIdentention(true);
        Familia f = new Familia("GEBARA");
        BaseEntity.useSimpleId=true;
        Pessoa duda, vicente, munif;
        f.setMembros(Arrays.asList(new Pessoa[]{munif = new Pessoa("Munif"), vicente = new Pessoa("Vicente"), duda = new Pessoa("Duda"), new Pessoa("Josil")}));
        duda.setIrmao(vicente);
        vicente.setIrmao(duda);
        f.getMembros().forEach((p) -> {
            BaseEntity.useSimpleId=true;
            p.setFamilia(f);
        });

        VicSerializer.setIdentention(false);
        assertEquals(VicSerializer.getInstance().serialize(vicente), VicSerializer.getInstance().serialize(vicente));

    }

}
