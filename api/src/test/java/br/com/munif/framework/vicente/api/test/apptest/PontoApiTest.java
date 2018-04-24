/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.api.test.apptest.LibaryApp;
import br.com.munif.framework.vicente.api.test.apptest.TestUtil;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibaryApp.class)
public class PontoApiTest {

    public static final String DEAFAULT_NAME = "Meu Ponto";

    @Autowired
    private PontoRepository repository;

    @Autowired
    private PontoService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    private Ponto ponto;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);
        final PontoApi pontoAPi = new PontoApi(service);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(pontoAPi)
//                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

        Ponto p = new Ponto();
        BaseEntityHelper.setBaseEntityFields(p);
        repository.save(p);

    }

    public static Ponto createEntity(EntityManager em) {
        Ponto ponto = new Ponto();
        BaseEntityHelper.setBaseEntityFields(ponto);
        ponto.setNome(DEAFAULT_NAME);
        return ponto;
    }

    public static Ponto createEntity() {
        Ponto ponto = new Ponto();
        BaseEntityHelper.setBaseEntityFields(ponto);
        ponto.setNome(DEAFAULT_NAME);
        return ponto;
    }

    private List<Ponto> findAll() {
        Iterable<Ponto> findAll = repository.findAll();

        List<Ponto> result = new ArrayList<>();
        for (Ponto p : findAll) {
            result.add(p);
        }

        return result;
    }

    private int count() {
        return (int) repository.count();
    }

    @Before
    public void initTest() {
        ponto = createEntity(em);
    }

    @Test
    @Transactional
    public void create() throws Exception {
        int databaseSizeBeforeCreate = count();

        // Create the Book
        restMockMvc.perform(post("/api/ponto")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createEntity())))
                .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Ponto> list = findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate + 1);
        Ponto test = list.get(list.size() - 1);
        assertThat(test.getNome()).isEqualTo(DEAFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAll() throws Exception {
        // Initialize the database
        repository.saveAndFlush(ponto);

        restMockMvc.perform(get("/api/ponto?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.values.[*].id").value(hasItem(ponto.getId())))
                .andExpect(jsonPath("$.values.[*].nome").value(hasItem(DEAFAULT_NAME)));

    }

    @Test
    @Transactional
    public void testaHora() throws Exception {
        // Initialize the database
        repository.saveAndFlush(ponto);
        MvcResult r = restMockMvc.perform(get("/api/ponto/"+ponto.getId())).andReturn();
        String contentAsString = r.getResponse().getContentAsString();
        System.out.println("---->" + contentAsString);
        String writeValueAsString = this.jacksonMessageConverter.getObjectMapper().writeValueAsString(ponto);
        assertEquals(writeValueAsString, contentAsString);

    }

    @Test
    public void conversao() throws Exception {
        Ponto p = new Ponto();
        BaseEntityHelper.setBaseEntityFields(p);
        byte[] convertObjectToJsonBytes = TestUtil.convertObjectToJsonBytes(p);
        String s = new String(convertObjectToJsonBytes);
        Map<String, Object> convertStringToMap = TestUtil.convertStringToMap(s);
        assertNotNull(convertStringToMap);

    }

}
