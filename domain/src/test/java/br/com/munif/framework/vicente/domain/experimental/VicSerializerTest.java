/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain.experimental;

import br.com.munif.framework.vicente.domain.BaseEntity;
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
        BaseEntity.simpleId = true;
        Familia f = new Familia("GEBARA");
        Pessoa duda, vicente, munif;
        f.setMembros(Arrays.asList(new Pessoa[]{munif = new Pessoa("Munif"), vicente = new Pessoa("Vicente"), duda = new Pessoa("Duda"), new Pessoa("Josil")}));
        duda.setIrmao(vicente);
        vicente.setIrmao(duda);
        f.getMembros().forEach((p) -> {
            p.setFamilia(f);
        });
        VicSerializer.setIdentention(true);
        System.out.println(VicSerializer.getInstance().serialize(vicente));
        
        VicSerializer.setIdentention(false);
        assertEquals("{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000002,extra:Framework,version:null,nome:Munif,irmao:null,familia:{class:br.com.munif.framework.vicente.domain.experimental.Familia,id:Fam000000001,extra:Framework,version:null,nome:GEBARA,membros:{class:java.util.Arrays.ArrayList,modCount:0,a:[{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000002,extra:Framework,version:null,nome:Munif,irmao:null,familia:{class:br.com.munif.framework.vicente.domain.experimental.Familia,id:Fam000000001}},{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000003,extra:Framework,version:null,nome:Vicente,irmao:{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000004,extra:Framework,version:null,nome:Duda,irmao:{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000003},familia:{class:br.com.munif.framework.vicente.domain.experimental.Familia,id:Fam000000001}},familia:{class:br.com.munif.framework.vicente.domain.experimental.Familia,id:Fam000000001}},{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000004,extra:Framework,version:null,nome:Duda,irmao:{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000003},familia:{class:br.com.munif.framework.vicente.domain.experimental.Familia,id:Fam000000001}},{class:br.com.munif.framework.vicente.domain.experimental.Pessoa,id:Pes000000005,extra:Framework,version:null,nome:Josil,irmao:null,familia:{class:br.com.munif.framework.vicente.domain.experimental.Familia,id:Fam000000001}}]}}}", VicSerializer.getInstance().serialize(munif));
        
    }
    
}
