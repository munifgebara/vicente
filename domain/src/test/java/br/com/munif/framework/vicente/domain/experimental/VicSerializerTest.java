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
        System.out.println("getInstance");
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
        BaseEntityHelper.setBaseEntityFieldsWithSimpleId(f);
        Pessoa duda, vicente, munif;
        f.setMembros(Arrays.asList(new Pessoa[]{munif = new Pessoa("Munif"), vicente = new Pessoa("Vicente"), duda = new Pessoa("Duda"), new Pessoa("Josil")}));
        duda.setIrmao(vicente);
        vicente.setIrmao(duda);
        f.getMembros().forEach((p) -> {
            BaseEntityHelper.setBaseEntityFieldsWithSimpleId(p);
            p.setFamilia(f);
        });
        System.out.println("\n\n\n\n\n");
        System.out.println(VicSerializer.getInstance().serialize(vicente));
        System.out.println("\n\n\n\n\n");

        VicSerializer.setIdentention(false);
        System.out.println(VicSerializer.getInstance().serialize(vicente));

        assertEquals("{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000003\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Vicente\",\"irmao\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000004\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Duda\",\"irmao\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000003\",\"version\":\"null\"},\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"GEBARA\",\"membros\":{\"class\":\"java.util.Arrays.ArrayList\",\"modCount\":\"0\",\"a\":[{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000002\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Munif\",\"irmao\":null,\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}},{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000003\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Vicente\",\"irmao\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000004\",\"version\":\"null\"},\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}},{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000004\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Duda\",\"irmao\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000003\",\"version\":\"null\"},\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}},{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000005\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Josil\",\"irmao\":null,\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}}]}}},\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"GEBARA\",\"membros\":{\"class\":\"java.util.Arrays.ArrayList\",\"modCount\":\"0\",\"a\":[{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000002\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Munif\",\"irmao\":null,\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}},{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000003\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Vicente\",\"irmao\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000004\",\"version\":\"null\"},\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}},{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000004\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Duda\",\"irmao\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000003\",\"version\":\"null\"},\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}},{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Pessoa\",\"id\":\"Pes000000005\",\"extra\":\"Framework\",\"version\":null,\"nome\":\"Josil\",\"irmao\":null,\"familia\":{\"class\":\"br.com.munif.framework.vicente.domain.experimental.Familia\",\"id\":\"Fam000000001\",\"version\":\"null\"}}]}}}", VicSerializer.getInstance().serialize(vicente));

    }

}