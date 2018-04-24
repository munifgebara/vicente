/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.VicAutoSeed;
import br.com.munif.framework.vicente.api.test.apptest.Book;
import br.com.munif.framework.vicente.api.test.apptest.BookApi;
import br.com.munif.framework.vicente.api.test.apptest.BookRepository;
import br.com.munif.framework.vicente.api.test.apptest.BookService;
import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import static br.com.munif.framework.vicente.api.test.apptest.BookApiTest.DEAFAULT_NAME;
import static br.com.munif.framework.vicente.api.test.apptest.BookApiTest.createEntity;
import br.com.munif.framework.vicente.api.test.apptest.LibaryApp;
import br.com.munif.framework.vicente.api.test.apptest.TestUtil;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicReturn;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
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
import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Ignore;

import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibaryApp.class)
public class SimpleApiTest {

    public static final String DEAFAULT_NAME = "The Book";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    private Book book;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);

        final BookApi bookAPi = new BookApi(service);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(bookAPi)
                //                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

    }

    @After
    public void end() {
        bookRepository.findAll().forEach((b) -> {
            System.out.println("------>" + b);
        });
    }

    @Test
    @Transactional
    public void createPut() throws Exception {
        long qtdOld = bookRepository.count();
        String contentAsString = restMockMvc.perform(get("/api/books/new")).andReturn().getResponse().getContentAsString();

        Map<String, Object> map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "POST");
        restMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());
        long qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);

    }

    @Test
    @Transactional
    public void createPutNoId() throws Exception {
        long qtdOld = bookRepository.count();
        String contentAsString = restMockMvc.perform(get("/api/books/new")).andReturn().getResponse().getContentAsString();
        Map<String, Object> map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "PUT_NO_ID");
        restMockMvc.perform(put("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());
        long qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);
    }

    @Test
    @Transactional
    public void createPutWithId() throws Exception {
        long qtdOld = bookRepository.count();
        String contentAsString = restMockMvc.perform(get("/api/books/new")).andReturn().getResponse().getContentAsString();
        Map<String, Object> map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "PUT_WITH_ID");
        restMockMvc.perform(put("/api/books/" + map.get("id"))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());
        long qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);
    }

    @Test
    @Transactional
    public void updateWithId() throws Exception {
        long qtdOld = bookRepository.count();
        String contentAsString = restMockMvc.perform(get("/api/books/new")).andReturn().getResponse().getContentAsString();
        Map<String, Object> map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "BEFORE UPDATE");
        restMockMvc.perform(put("/api/books/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());
        long qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);
        System.out.println("----------->" + bookRepository.findAll());
        contentAsString = restMockMvc.perform(get("/api/books/1")).andReturn().getResponse().getContentAsString();
        map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "AFTER UPDATE");
        restMockMvc.perform(put("/api/books/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isOk());
        qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);

        System.out.println("----------->" + bookRepository.findAll());
    }

    @Test
    @Transactional
    public void updateWithoutId() throws Exception {
        long qtdOld = bookRepository.count();
        String contentAsString = restMockMvc.perform(get("/api/books/new")).andReturn().getResponse().getContentAsString();
        Map<String, Object> map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "BEFORE UPDATE");
        restMockMvc.perform(put("/api/books/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());
        long qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);
        System.out.println("----------->" + bookRepository.findAll());
        contentAsString = restMockMvc.perform(get("/api/books/1")).andReturn().getResponse().getContentAsString();
        map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "AFTER UPDATE");
        restMockMvc.perform(put("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isOk());
        qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);

        System.out.println("----------->" + bookRepository.findAll());
    }

    @Test
    @Transactional
    public void deleteOne() throws Exception {
        long qtdOld = bookRepository.count();
        String contentAsString = restMockMvc.perform(get("/api/books/new")).andReturn().getResponse().getContentAsString();
        Map<String, Object> map = TestUtil.convertStringToMap(contentAsString);
        map.put("name", "BEFORE DELETE");
        restMockMvc.perform(put("/api/books/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());
        long qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld + 1);
        restMockMvc.perform(delete("/api/books/{id}", "1")
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        qtdNew = bookRepository.count();
        assert (qtdNew == qtdOld);
        System.out.println("----------->" + bookRepository.findAll());
    }

}
