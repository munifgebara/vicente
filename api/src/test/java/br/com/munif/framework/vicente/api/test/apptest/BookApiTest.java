/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.test.apptest.Book;
import br.com.munif.framework.vicente.api.test.apptest.BookApi;
import br.com.munif.framework.vicente.api.test.apptest.BookRepository;
import br.com.munif.framework.vicente.api.test.apptest.BookService;
import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.api.test.apptest.LibaryApp;
import br.com.munif.framework.vicente.api.test.apptest.TestUtil;
import br.com.munif.framework.vicente.core.VicThreadScope;
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
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibaryApp.class)
public class BookApiTest {

    public static final String DEAFAULT_NAME = "The Book";

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

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
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    public static Book createEntity(EntityManager em) {
        Book book = new Book();
        book.setName(DEAFAULT_NAME);
        return book;
    }

    public static Book createEntity() {
        Book book = new Book();
        book.setName(DEAFAULT_NAME);
        return book;
    }

    private List<Book> findAll() {
        Iterable<Book> findAll = repository.findAll();

        List<Book> result = new ArrayList<>();
        for (Book r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count() {
        return (int) repository.count();
    }

    @Before
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void create() throws Exception {
        int databaseSizeBeforeCreate = count();

        // Create the Book
        restMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createEntity())))
                .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Book> bookList = findAll();
        System.out.println("--->" + bookList);
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEAFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWithExistingId() throws Exception {
        repository.saveAndFlush(book);
        int databaseSizeBeforeCreate = findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isBadRequest());

        // Validate the Cargo in the database
        List<Book> list = findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

}
