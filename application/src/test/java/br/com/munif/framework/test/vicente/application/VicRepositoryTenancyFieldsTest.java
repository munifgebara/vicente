package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.application.repository.PessoaRepository;
import br.com.munif.framework.test.vicente.application.service.PessoaService;
import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldRepository;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldService;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldType;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldValue;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author munif
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2SpringConfig.class})
public class VicRepositoryTenancyFieldsTest {

    @Autowired
    protected PessoaRepository pessoaRepository;

    @Autowired
    protected PessoaService pessoaService;

    @Autowired
    protected VicFieldService vicFieldService;

    @Autowired
    protected VicFieldRepository vicFieldRepository;

    @Before
    @Transactional
    public void setUp() {
        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");
        pessoaRepository.deleteAll();
        vicFieldRepository.deleteAll();
        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        pessoaRepository.deleteAll();
        vicFieldRepository.deleteAll();

        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");
        VicField time = vicFieldService.newEntity();
        time.setClazz(Pessoa.class.getCanonicalName());
        time.setName("time");
        time.setFieldType(VicFieldType.TEXT);
        time.setDefaultValueScript("'novo'");
        vicFieldService.save(time);

        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        VicField religiao = vicFieldService.newEntity();
        religiao.setClazz(Pessoa.class.getCanonicalName());
        religiao.setName("religião");
        religiao.setFieldType(VicFieldType.TEXT);
        religiao.setDefaultValueScript("'sem'");
        vicFieldService.save(religiao);

        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");

        Pessoa px = pessoaService.newEntity();
        px.setNome("Pessoa X");
        px.getVicTenancyFields().put("time", new VicFieldValue(time, px.getId(), "COXA"));
        pessoaService.save(px);

        Pessoa pz = pessoaService.newEntity();
        pz.setNome("Pessoa Z");
        pz.getVicTenancyFields().put("time", new VicFieldValue(time, pz.getId(), "Vasco da Gama"));
        pessoaService.save(pz);

        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        Pessoa py = pessoaService.newEntity();
        py.setNome("Pessoa Y");
        py.getVicTenancyFields().put("religião", new VicFieldValue(religiao, py.getId(), "Católica"));
        pessoaService.save(py);

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

    @Test
    @Transactional
    public void new000() {
        VicThreadScope.ui.set("UZT");
        VicThreadScope.gi.set("GZT");
        Pessoa p = pessoaService.newEntity();
        System.out.println("--->" + p);
        assertEquals("novo", p.getVicTenancyFields().get("time").getValue());
    }

    @Test
    @Transactional
    public void new001() {
        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        Pessoa p = pessoaService.newEntity();
        System.out.println("--->" + p);
        assertEquals("sem", p.getVicTenancyFields().get("religião").getValue());
    }

}
