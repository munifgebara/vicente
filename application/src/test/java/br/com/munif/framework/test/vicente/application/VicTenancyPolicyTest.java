package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.application.service.ConsultorService;
import br.com.munif.framework.test.vicente.application.service.FuncionarioService;
import br.com.munif.framework.test.vicente.application.service.PessoaService;
import br.com.munif.framework.test.vicente.domain.model.Consultor;
import br.com.munif.framework.test.vicente.domain.model.Funcionario;
import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.core.VicThreadScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author munif
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2SpringConfig.class})
public class VicTenancyPolicyTest {

    @Autowired
    protected FuncionarioService funcionarioService;
    @Autowired
    protected ConsultorService consultorService;
    @Autowired
    protected PessoaService pessoaService;

    @Before
    @Transactional
    public void setUp() {
        System.out.println("Setup of Test class " + this.getClass().getSimpleName() + " " + funcionarioService.count());
        if (funcionarioService.findAll().size() > 0) {
            return;
        }
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.gi.set("GRUPO");

        for (int i = 0; i < 1000; i++) {
            VicThreadScope.oi.set(1 + i / 100 + "." + (1 + (i / 10) % 10) + "." + (1 + i % 10) + ".");
            Funcionario f = funcionarioService.newEntity();
            f.setNome("Funcionario " + i);
            funcionarioService.save(f);

            Consultor c = consultorService.newEntity();
            c.setNome("Consultor " + i);
            consultorService.save(c);
        }

        VicThreadScope.oi.set("A.B.C.");
        Funcionario f = funcionarioService.newEntity();
        f.setNome("Funcionario ABC");
        funcionarioService.save(f);

    }

    @Test
    @Transactional
    public void defaultPolicy() {
        Pessoa p = pessoaService.newEntity();
        assertEquals(VicTenancyType.GROUPS, p.getTencyPolicy());
    }

    @Test
    @Transactional
    public void otherPolicy() {
        Funcionario f = funcionarioService.newEntity();
        assertEquals(VicTenancyType.HIERARCHICAL_TOP_DOWN, f.getTencyPolicy());
    }


    @Test
    @Transactional
    public void findAllFromMainLevelHierarchicalTopDownAndOwner() {
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.");
        List<Funcionario> findAllNoPublic = funcionarioService.findAllNoPublic();
        assertEquals(1001, findAllNoPublic.size());
    }

    @Test
    @Transactional
    public void findAllFromMainLevelHierarchicalTopDown() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.");
        List<Funcionario> findAllNoPublic = funcionarioService.findAllNoPublic();
        assertEquals(100, findAllNoPublic.size());
    }

    @Test
    @Transactional
    public void findAllFromSecondLevelHierarchicalTopDown() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.1.");
        List<Funcionario> findAllNoPublic = funcionarioService.findAllNoPublic();
        assertEquals(10, findAllNoPublic.size());
    }

    @Test
    @Transactional
    public void findAllFromBottonLevelHierarchicalTopDown() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.1.1.");
        List<Funcionario> findAllNoPublic = funcionarioService.findAllNoPublic();
        assertEquals(1, findAllNoPublic.size());
    }

    @Test
    @Transactional
    public void findAllFromMainLevelOrganizational() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.");
        List<Consultor> findAllNoPublic = consultorService.findAllNoPublic();
        assertEquals(100, findAllNoPublic.size());
    }

    @Test
    @Transactional
    public void findAllFromSecondLevelOrganizational() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.1.");
        List<Consultor> findAllNoPublic = consultorService.findAllNoPublic();
        assertEquals(100, findAllNoPublic.size());
    }

    @Test
    @Transactional
    public void findAllFromBottonLevelOrganizational() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        VicThreadScope.oi.set("1.1.1.");
        List<Consultor> findAllNoPublic = consultorService.findAllNoPublic();
        assertEquals(100, findAllNoPublic.size());
    }

}
