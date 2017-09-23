/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.vicente.application.search.VicSmartSearch;
import br.com.munif.framework.vicente.core.VicThreadScope;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author munif
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySQLSpringConfig.class})
public class VicSmartSearchTest {

    @Autowired
    protected VicSmartSearch vss;

    @Autowired
    protected PessoaService pessoaService;

    @Before
    @Transactional
    public void setUp() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
    }


    @Test
    @Transactional
    public void emNotNull() {
        assertNotNull(vss.getEm());
    }

    @Test
    @Transactional
    public void teste() {
        vss.smartSearch("Cliente", "Categora", "nome=s");
    }

    
}
