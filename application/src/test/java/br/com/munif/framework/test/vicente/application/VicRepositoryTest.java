package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.application.model.Pessoa;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author munif
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MySQLSpringConfig.class})
public class VicRepositoryTest {

    @Autowired
    protected PessoaService pessoaService;

    @Before
    @Transactional
    public void setUp() {
        if (pessoaService.findAll().size() > 0) {
            return;
        }
        for (int i = 0; i < 100; i++) {
            VicThreadScope.ui.set("U" + (1 + i % 10));
            VicThreadScope.gi.set("G1" + (1 + i / 10));
            Pessoa p = new Pessoa();
            p.setNome("Pessoa " + i);
            pessoaService.save(p);
        }
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZ");
        Pessoa p = new Pessoa();
        p.setNome("Pessoa Z");
        p.setRights(RightsHelper.OTHER_READ);
        pessoaService.save(p);
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZ");

        Pessoa p2 = new Pessoa();
        p2.setNome("Pessoa J");
        p2.setRights(0);
        pessoaService.save(p2);

    }

    @Test
    @Transactional
    public void findALl() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        List<Pessoa> findAll = pessoaService.findAll();
        System.out.println("---->" + findAll);
        assertEquals(11, findAll.size());

    }

    @Test
    @Transactional
    public void findALl2() {
        VicThreadScope.ui.set("U11");
        VicThreadScope.gi.set("G1");
        List<Pessoa> findAll = pessoaService.findAll();
        System.out.println("---->" + findAll);
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findALl3() {
        VicThreadScope.ui.set("U11");
        VicThreadScope.gi.set("G19");
        List<Pessoa> findAll = pessoaService.findAll();
        System.out.println("---->" + findAll);
        assertEquals(11, findAll.size());
    }

    @Test
    @Transactional
    public void findALl4() {
        VicThreadScope.ui.set("U00");
        VicThreadScope.gi.set("G11");
        List<Pessoa> findAll = pessoaService.findAll();
        System.out.println("---->" + findAll);
        assertEquals(11, findAll.size());
    }

    @Test
    @Transactional
    public void findALl5() {
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZZ");
        List<Pessoa> findAll = pessoaService.findAll();
        System.out.println("---->" + findAll);
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findALl6() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        List<Pessoa> findAll = pessoaService.findAll();
        System.out.println("---->" + findAll);
        assertEquals(21, findAll.size());
    }


}