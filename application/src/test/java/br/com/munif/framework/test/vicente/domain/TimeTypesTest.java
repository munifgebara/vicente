package br.com.munif.framework.test.vicente.domain;

import br.com.munif.framework.test.vicente.application.MySQLSpringConfig;
import br.com.munif.framework.test.vicente.application.PontoService;
import br.com.munif.framework.test.vicente.domain.model.Ponto;
import br.com.munif.framework.vicente.core.VicThreadScope;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySQLSpringConfig.class})
public class TimeTypesTest {

    private Ponto entity;
    
    @Autowired
    private PontoService pontoService;
    
    


    @Before
    @Transactional
    public void setUp() {
        VicThreadScope.ui.set("ZZ");
        VicThreadScope.gi.set("ZZ");
        this.entity = pontoService.newEntity();
        pontoService.save(entity);
    }

    @Test
    @Transactional
    public void recuperaPonto() {
        List<Ponto> todos = pontoService.findAll();
        assertEquals(1, todos.size());
    }
    
    @Test
    @Transactional
    public void calculaDiferenca() {
        List<Ponto> todos = pontoService.findAll();
        Ponto primeiro = todos.get(0);
        long diferenca = ChronoUnit.HOURS.between(primeiro.getEntrada(), primeiro.getSaida());
        assertEquals(4, diferenca);
        
    }

    
}
