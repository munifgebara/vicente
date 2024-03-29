package br.com.munif.framework.test.vicente.application;

import br.com.munif.framework.test.vicente.application.repository.PessoaRepository;
import br.com.munif.framework.test.vicente.application.service.PessoaService;
import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.application.VicRepositoryUtil;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.UIDHelper;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.*;
import br.com.munif.framework.vicente.domain.typings.PhoneType;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author munif
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2SpringConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VicRepositoryTest {

    @Autowired
    protected PessoaService pessoaService;
    @Autowired
    protected PessoaRepository pessoaRepository;

    @Before
    @Transactional
    public void setUp() {
        if (pessoaService.findAll().size() > 0) {
            return;
        }
        for (int i = 0; i < 100; i++) {
            VicThreadScope.ui.set("U" + (1 + i % 10));
            VicThreadScope.gi.set("G1" + (1 + i / 10));
            Pessoa p = pessoaService.newEntity();
            p.setNome("Pessoa " + i);
            pessoaService.save(p);
        }
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZ");
        Pessoa p = pessoaService.newEntity();
        p.setNome("Pessoa Z");
        p.setRights(RightsHelper.OTHER_READ);
        pessoaService.save(p);
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZ");

        Pessoa p2 = pessoaService.newEntity();
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
        assertEquals(10, findAllNoPublic.size());

    }

    @Test
    @Transactional
    public void findALl() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G1");
        List<Pessoa> findAll = pessoaService.findAll();
        assertEquals(10, findAll.size());

    }

    @Test
    @Transactional
    public void findALl5() {
        VicThreadScope.ui.set("UZ");
        VicThreadScope.gi.set("GZ");
        List<Pessoa> findAll = pessoaService.findAll();
        assertEquals(2, findAll.size());
    }

    @Test
    @Transactional
    public void findALl6() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15,");
        List<Pessoa> findAll = pessoaService.findAll();
        //System.out.println("---->" + findAll);
        assertEquals(20, findAll.size());
    }

    @Test
    @Transactional
    public void findByHql() {
        VicThreadScope.ui.set("U1");
        VicThreadScope.gi.set("G11,G15,");
        List<Pessoa> findAll = pessoaService.findByHql(new VicQuery());
        assertEquals(28, findAll.size());
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
        assertEquals(2, findAll.size());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    @Transactional
    public void saveNull() {
        VicThreadScope.ui.set("XXX");
        VicThreadScope.gi.set("XXXXX");
        Pessoa p = pessoaService.newEntity();
        p.setNome(null);
        pessoaService.save(p);
    }

    @Test
    @Transactional
    public void findByHqlAndQuery() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15,");
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
        VicThreadScope.gi.set("G11,G15,");
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
        VicThreadScope.gi.set("G11,G15,");
        VicQuery q = new VicQuery();
        q.setQuery(new VQuery(new Criteria("nome", ComparisonOperator.NOT_CONTAINS, "1")));
        List<Pessoa> findAll = pessoaService.findByHql(q);
        assertEquals(18, findAll.size());
    }

    @Test
    @Transactional
    public void findByQuery2In() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15,");
        VicQuery q = new VicQuery();
        q.setQuery(new VQuery(
                new Criteria(
                        "id", ComparisonOperator.IN, new VEntityQuery("Pessoa", "p", new Criteria("nome", ComparisonOperator.CONTAINS, "1"), "id")
                )
        ));
        List<Pessoa> findAll = pessoaService.findByHql(q);
        assertEquals(2, findAll.size());
    }

    @Test
    public void getSetUpdate() {
        HashMap<String, Object> map = new HashMap<>();
        String uid = UIDHelper.getUID();
        Date date = new Date();
        map.put("id", uid);
        map.put("nome", "PESSOA ALTERADA");
        map.put("nascimento", date);
        SetUpdateQuery setUpdate = VicRepositoryUtil.getSetUpdate(map);
    }

    @Test
    public void patch() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        List<Pessoa> all = pessoaService.findAll();
        Pessoa pessoa = all.get(0);
        HashMap<String, Object> pessoaAlterada = new HashMap<>();
        pessoaAlterada.put("id", pessoa.getId());
        pessoaAlterada.put("nome", "NOVO NOME DIFERENTE");

        HashMap<String, Object> telefone = new HashMap<>();
        telefone.put("description", "888888888");
        telefone.put("type", PhoneType.LANDLINE.name());
        pessoaAlterada.put("telefone", telefone);
        pessoaService.patch(pessoaAlterada);

        Pessoa load = pessoaService.load(pessoa.getId());
        assertEquals("NOVO NOME DIFERENTE", load.getNome());
        assertEquals("888888888", load.getTelefone().getDescription());
        assertEquals(load.getTelefone().getType(), load.getTelefone().getType());
        assertNotEquals(load.getNome(), pessoa.getNome());
        assertEquals(load.getApelido(), pessoa.getApelido());
        assertEquals(load.getDocumento(), pessoa.getDocumento());
    }

    @Test
    public void patchReturning() {
        VicThreadScope.ui.set("U1001");
        VicThreadScope.gi.set("G11,G15");
        List<Pessoa> all = pessoaService.findAll();
        Pessoa pessoa = all.get(0);
        HashMap<String, Object> pessoaAlterada = new HashMap<>();
        pessoaAlterada.put("id", pessoa.getId());
        pessoaAlterada.put("nome", "NOVO NOME");

        HashMap<String, Object> telefone = new HashMap<>();
        telefone.put("description", "999999999");
        telefone.put("type", PhoneType.LANDLINE);
        pessoaAlterada.put("telefone", telefone);
        Pessoa pessoaReturning = pessoaService.patchReturning(pessoaAlterada);
        Pessoa load = pessoaService.load(pessoa.getId());

        assertEquals(pessoaReturning, load);
        assertEquals("NOVO NOME", load.getNome());
        assertEquals("999999999", load.getTelefone().getDescription());
        assertEquals(load.getTelefone().getType(), load.getTelefone().getType());
        assertNotEquals(load.getNome(), pessoa.getNome());
        assertEquals(load.getApelido(), pessoa.getApelido());
        assertEquals(load.getDocumento(), pessoa.getDocumento());
    }

    @Test
    public void isNew() {
        Pessoa p2 = pessoaService.newEntity();
        p2.setNome("Pessoa Não existente");
        p2.setRights(0);
        assertTrue(pessoaService.isNew(p2.getId()));

        pessoaService.save(p2);
        assertFalse(pessoaService.isNew(p2.getId()));
    }
}