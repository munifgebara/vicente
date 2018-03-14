package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldService;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldType;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldValue;
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
@ContextConfiguration(classes = {MySQLSpringConfig.class})
public class VicRepositoryTenancyFieldsTest {

    @Autowired
    protected PessoaService pessoaService;

    @Autowired
    protected VicFieldService vicFieldService;

    @Before
    @Transactional
    public void setUp() {
        System.out.println("Setup of Test class "+this.getClass().getSimpleName()+" "+pessoaService.findAllNoPublic().size());
        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");

        if (pessoaService.findAllNoPublic().size() > 0) {
            System.out.println("---> SETUP OK ");
            return;
        }
        System.out.println("---> SETUP");
        VicField vf = new VicField();
        vf.setClazz(Pessoa.class.getCanonicalName());
        vf.setName("time");
        vf.setFieldType(VicFieldType.TEXT);
        vf.setDefaultValueScript("'novo'");
        vicFieldService.save(vf);

        Pessoa p = new Pessoa();
        p.setNome("Pessoa X");
        p.getVicTenancyFields().put("time", new VicFieldValue(vf, null, "COXA"));
        pessoaService.save(p);

        p = new Pessoa();
        p.setNome("Pessoa Z");
        p.getVicTenancyFields().put("time", new VicFieldValue(vf, null, "Vasco da Gama"));
        pessoaService.save(p);

        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        vf = new VicField();
        vf.setClazz(Pessoa.class.getCanonicalName());
        vf.setName("religião");
        vf.setFieldType(VicFieldType.TEXT);
        vf.setDefaultValueScript("'sem'");
        vicFieldService.save(vf);

        p = new Pessoa();
        p.setNome("Pessoa Y");
        p.getVicTenancyFields().put("religião", new VicFieldValue(vf, null, "Católica"));
        pessoaService.save(p);

    }

    @Test
    @Transactional
    public void findALL00() {
        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");
        List<VicField> findAll = vicFieldService.findAll();
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findALL01() {
        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        List<Pessoa> findAll = pessoaService.findAllNoPublic();
        for (Pessoa p : findAll) {
            System.out.println("" + p.getId() + " " + p.getNome() + " " + p.getVicTenancyFields());
        }
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findALL02() {
        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");
        List<Pessoa> findAll = pessoaService.findAllNoPublic();
        for (Pessoa p : findAll) {
            System.out.println("" + p.getId() + " " + p.getNome() + " " + p.getVicTenancyFields());
        }
        assertEquals(2, findAll.size());
    }


}
