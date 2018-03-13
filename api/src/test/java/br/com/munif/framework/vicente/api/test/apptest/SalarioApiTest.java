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
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import java.math.BigDecimal;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibaryApp.class)
public class SalarioApiTest {

    public static final String DEAFAULT_NAME = "Meu Salario";

    @Autowired
    private SalarioRepository repository;

    @Autowired
    private SalarioService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    private Salario salario = new Salario(DEAFAULT_NAME, BigDecimal.TEN);

    private static int contador = 10000;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        final SalarioApi api = new SalarioApi(service);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(api)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
        contador++;
        VicThreadScope.ip.set("127.0.0.1");
        VicThreadScope.ui.set("USUARIO" + contador);
        VicThreadScope.gi.set("GRUPO" + contador);
        VicThreadScope.oi.set(contador + ".");
        VicThreadScope.defaultRights.set(RightsHelper.OWNER_ALL | RightsHelper.GROUP_ALL);
        for (long i = 1000; i < 10000; i += 1000) {
            Salario s = new Salario("Munif " + i, BigDecimal.valueOf(i));
            s.setStartTime(i);
            s.setEndTime(i + 999);
            Salario ss = repository.saveAndFlush(s);
        }
    }

    private List<Salario> findAll() {
        Iterable<Salario> findAll = repository.findAllNoTenancy();

        List<Salario> result = new ArrayList<>();
        for (Salario p : findAll) {
            if (p.valid()) {
                result.add(p);
            }
        }

        return result;
    }

    @Test
    @Transactional
    public void create() throws Exception {
        VicThreadScope.effectiveTime.set(55005l);
        int databaseSizeBeforeCreate = findAll().size();

        // Create the Salario
        restMockMvc.perform(post("/api/salario")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new Salario(DEAFAULT_NAME, BigDecimal.TEN))))
                .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Salario> list = findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate + 1);
        Salario test = list.get(list.size() - 1);
        assertThat(test.getNome()).isEqualTo(DEAFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAll() throws Exception {
        VicThreadScope.effectiveTime.set(155005l);
        salario = new Salario(DEAFAULT_NAME, BigDecimal.TEN);
        // Initialize the database
        repository.saveAndFlush(salario);

        restMockMvc.perform(get("/api/salario?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.values.[*].id").value(hasItem(salario.getId())))
                .andExpect(jsonPath("$.values.[*].nome").value(hasItem(DEAFAULT_NAME)));

    }
    
    


    @Test
    @Transactional
    public void testaHora() throws Exception {
        VicThreadScope.effectiveTime.set(255005l);
        salario = new Salario(DEAFAULT_NAME, BigDecimal.TEN);
        // Initialize the database
        repository.saveAndFlush(salario);
        MvcResult r = restMockMvc.perform(get("/api/salario/" + salario.getId())).andReturn();
        String contentAsString = r.getResponse().getContentAsString();
        System.out.println("---->" + contentAsString);
        String writeValueAsString = this.jacksonMessageConverter.getObjectMapper().writeValueAsString(salario);
        assertEquals(writeValueAsString, contentAsString);

    }

    @Test
    public void conversao() throws Exception {
        Salario p = new Salario();
        byte[] convertObjectToJsonBytes = TestUtil.convertObjectToJsonBytes(p);
        String s = new String(convertObjectToJsonBytes);
        Map<String, Object> convertStringToMap = TestUtil.convertStringToMap(s);
        assertNotNull(convertStringToMap);

    }

}
