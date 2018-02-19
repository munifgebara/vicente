package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
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
        System.out.println("Setup of Test class " + this.getClass().getSimpleName() + " " + pessoaService.quantidade());
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
    public void findALNoPublic() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        List<Pessoa> findAllNoPublic = pessoaService.findAllNoPublic();
        //System.out.println("---->" + findAll);
        assertEquals(10, findAllNoPublic.size());

    }


    @Test
    @Transactional
    public void findALl() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(11, findAll.size());

    }

    @Test
    @Transactional
    public void findALl2() {
        VicThreadScope.ui.set("U11");
        VicThreadScope.gi.set("G1");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findALl3() {
        VicThreadScope.ui.set("U11");
        VicThreadScope.gi.set("G19");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(11, findAll.size());
    }

    @Test
    @Transactional
    public void findALl4() {
        VicThreadScope.ui.set("U00");
        VicThreadScope.gi.set("G11");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(11, findAll.size());
    }

    @Test
    @Transactional
    public void findALl5() {
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZZ");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findALl6() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(21, findAll.size());
    }

    @Test
    @Transactional
    public void findByHql() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        List<Pessoa> findAll = pessoaService.findByHql(new VicQuery());
        //System.out.println("---->" + findAll);
        assertEquals(21, findAll.size());
    }

    @Test
    @Transactional
    public void findByHql2() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        VicQuery q = new VicQuery();
        q.setMaxResults(2);
        q.setHql("obj.nome like '%'");
        List<Pessoa> findAll = pessoaService.findByHql(q);
        //System.out.println("---->" + findAll);
        assertEquals(2, findAll.size());
    }


    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    @Transactional
    public void saveNull() {
        VicThreadScope.ui.set("XXX");
        VicThreadScope.gi.set("XXXXX");
        Pessoa p = new Pessoa();
        p.setNome(null);
        pessoaService.save(p);
    }

    @Test
    @Transactional
    public void findByHqlAndQuery() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        VicQuery vicQuery = new VicQuery();
        vicQuery.setHql("obj.nome like '%3%'");
        vicQuery.setQuery(new VQuery(new Criteria("nome", ComparisonOperator.CONTAINS, "4")));
        List<Pessoa> findAll = pessoaService.findByHql(vicQuery);
        assertEquals(1, findAll.size());
    }

    @Test
    @Transactional
    public void findByHql2AndQueryWithLeftJoin() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        VicQuery q = new VicQuery();
        q.setMaxResults(2);
        q.setHql("obj.nome like '%'");
        q.setQuery(new VQuery().join(new Join("obj.outrosEnderecos", JoinType.LEFT)));
        List<Pessoa> findAll = pessoaService.findByHql(q);
        assertEquals(2, findAll.size());
    }

    @Test
    @Transactional
    public void findByHql2AndQueryWithJoin() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        VicQuery q = new VicQuery();
        q.setMaxResults(2);
        q.setHql("obj.nome like '%'");
        q.setQuery(new VQuery().join(new Join("obj.outrosEnderecos", JoinType.SIMPLE)));
        List<Pessoa> findAll = pessoaService.findByHql(q);
        assertEquals(0, findAll.size());
    }

    @Test
    @Transactional
    public void findByQuery1() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        VicQuery q = new VicQuery();
        q.setQuery(new VQuery(new Criteria("nome", ComparisonOperator.NOT_CONTAINS, "1")));
        List<Pessoa> findAll = pessoaService.findByHql(q);
        assertEquals(19, findAll.size());
    }

    @Test
    @Transactional
    public void findByQuery2In() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        VicQuery q = new VicQuery();
        q.setQuery(new VQuery(
                new Criteria(
                        "id", ComparisonOperator.IN, new VEntityQuery("Pessoa", "p", new Criteria("nome", ComparisonOperator.CONTAINS, "1"), "id")
                )
        ));
        List<Pessoa> findAll = pessoaService.findByHql(q);
        assertEquals(2, findAll.size());
    }

}
