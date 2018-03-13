package br.com.munif.framework.test.vicente.domain;

import br.com.munif.framework.test.vicente.application.MySQLSpringConfig;
import br.com.munif.framework.test.vicente.application.PontoService;
import br.com.munif.framework.test.vicente.application.SalarioRepository;
import br.com.munif.framework.test.vicente.application.SalarioService;
import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.test.vicente.domain.model.Ponto;
import br.com.munif.framework.test.vicente.domain.model.Salario;
import br.com.munif.framework.vicente.core.VicThreadScope;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySQLSpringConfig.class})
public class VicTemporalTest {

    @Autowired
    private SalarioRepository repository;

    @Autowired
    private SalarioService service;

    @Before
    @Transactional
    public void setUp() {
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.effectiveTime.set(55000l);
        for (long i = 1000; i < 100000; i += 1000) {
            Salario s = new Salario("Munif " + i, BigDecimal.valueOf(i));
            s.setStartTime(i);
            s.setEndTime(i + 999);
            Salario ss = repository.saveAndFlush(s);
            System.out.println("--->" + ss);
        }
    }

    @Test
    @Transactional
    public void recupera1() {
        List<Salario> todos = service.findAll();
        assertEquals(1, todos.size());
    }

}
