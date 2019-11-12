/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.api.test.apptest.api.PessoaGenericaApi;
import br.com.munif.framework.vicente.api.test.apptest.domain.PessoaGenerica;
import br.com.munif.framework.vicente.api.test.apptest.repository.PessoaGenericaRepository;
import br.com.munif.framework.vicente.api.test.apptest.service.PessoaGenericaService;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldRepository;
import br.com.munif.framework.vicente.application.victenancyfields.VicFieldService;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldType;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldValue;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertNotNull;

import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InformationApp.class)
public class PessoaGenericaApiTest {

    private static final String DEAFAULT_NAME = "Vicente";

    @Autowired
    private PessoaGenericaRepository repository;

    @Autowired
    private PessoaGenericaService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    private PessoaGenerica pessoa;

    @Autowired
    protected VicFieldService vicFieldService;

    @Autowired
    protected VicFieldRepository vicFieldRepository;
    private VicField religiao;
    private VicField time;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);

        final PessoaGenericaApi api = new PessoaGenericaApi(service);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(api)
                //                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

        repository.deleteAll();
        vicFieldRepository.deleteAll();
        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        repository.deleteAll();
        vicFieldRepository.deleteAll();

        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        time = vicFieldService.newEntity();
        time.setClazz(PessoaGenerica.class.getCanonicalName());
        time.setName("time");
        time.setFieldType(VicFieldType.TEXT);
        time.setDefaultValueScript("'novo'");
        vicFieldService.save(time);

        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        VicThreadScope.oi.set("2.");
        religiao = vicFieldService.newEntity();
        religiao.setClazz(PessoaGenerica.class.getCanonicalName());
        religiao.setName("religião");
        religiao.setFieldType(VicFieldType.TEXT);
        religiao.setDefaultValueScript("'sem'");
        vicFieldService.save(religiao);

        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");

        PessoaGenerica px = service.newEntity();
        px.setNome("PessoaGenerica X");
        px.getVicTenancyFields().put("time", new VicFieldValue(time, px.getId(), "COXA"));
        service.save(px);

        PessoaGenerica pz = service.newEntity();
        pz.setNome("PessoaGenerica Z");
        pz.getVicTenancyFields().put("time", new VicFieldValue(time, pz.getId(), "Vasco da Gama"));
        service.save(pz);

        VicThreadScope.ui.set("UXT");
        VicThreadScope.gi.set("GXT");
        VicThreadScope.oi.set("2.");
        PessoaGenerica py = service.newEntity();
        py.setNome("PessoaGenerica Y");
        py.getVicTenancyFields().put("religião", new VicFieldValue(religiao, py.getId(), "Católica"));
        service.save(py);

    }

    public PessoaGenerica createEntity(EntityManager em) {
        PessoaGenerica pg = new PessoaGenerica();
        pg.setNome(DEAFAULT_NAME);
        return pg;
    }

    public PessoaGenerica createEntity() {
        PessoaGenerica pg = new PessoaGenerica();
        pg.setNome(DEAFAULT_NAME);
        return pg;
    }

    private List<PessoaGenerica> findAll() {
        Iterable<PessoaGenerica> findAll = repository.findAll();

        List<PessoaGenerica> result = new ArrayList<>();
        for (PessoaGenerica r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count() {
        return (int) repository.findAll().size();
    }

    @Before
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void create() throws Exception {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");

        int databaseSizeBeforeCreate = count();
        PessoaGenerica createEntity = createEntity();
        createEntity.getVicTenancyFields().put("time", new VicFieldValue(time, createEntity.getId(), "Ponte Petra"));
        // Create the Book
        restMockMvc.perform(post("/api/pessoagenerica")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createEntity)))
                .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<PessoaGenerica> list = findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate + 1);
        PessoaGenerica test = list.get(list.size() - 1);
        assertThat(test.getNome()).isEqualTo(DEAFAULT_NAME);
    }

    @Test
    @Transactional
    public void listTest() throws Exception {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");

        ResultActions r = restMockMvc.perform(get("/api/pessoagenerica?sort=id,desc")).andExpect(status().isOk());
        String toString = r.andReturn().getResponse().getContentAsString();
        assertNotNull(toString);

    }

    @Test
    @Transactional
    public void newTest() throws Exception {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");

        ResultActions r = restMockMvc.perform(get("/api/pessoagenerica/new")).andExpect(status().isOk());
        String toString = r.andReturn().getResponse().getContentAsString();
        assertNotNull(toString);

    }
}
