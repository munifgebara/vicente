/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.VicAutoSeed;
import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.api.test.apptest.api.BookApi;
import br.com.munif.framework.vicente.api.test.apptest.domain.Book;
import br.com.munif.framework.vicente.api.test.apptest.repository.BookRepository;
import br.com.munif.framework.vicente.api.test.apptest.service.BookService;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicReturn;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InformationApp.class)
public class BookApiTest {

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

        Book b = new Book();
        bookRepository.saveAll(VicAutoSeed.getInteligentInstances(b, 10));

    }

    private List<Book> findAll() {
        Iterable<Book> findAll = bookRepository.findAll();

        List<Book> result = new ArrayList<>();
        for (Book r : findAll) {
            result.add(r);
        }

        return result;
    }

    private int count() {
        return (int) bookRepository.count();
    }

    @Before
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void create() throws Exception {
        int databaseSizeBeforeCreate = count();
        Book createEntity = createEntity();
        createEntity.setId(null);
        // Create the Book
        restMockMvc.perform(post("/api/books")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(createEntity)))
                .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Book> bookList = findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEAFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWithExistingId() throws Exception {
        bookRepository.saveAndFlush(book);
        int databaseSizeBeforeCreate = findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMockMvc.perform(post("/api/books")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isConflict());

        List<Book> list = findAll();
        assertThat(list).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAll() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        restMockMvc.perform(get("/api/books?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.values.[*].id").value(hasItem(book.getId())))
                .andExpect(jsonPath("$.values.[*].name").value(hasItem(DEAFAULT_NAME)));

    }

    @Test
    @Transactional
    public void getAll2() throws Exception {

        ResultActions r = restMockMvc.perform(get("/api/books?sort=id,desc")).andExpect(status().isOk());
        String toString = r.andReturn().getResponse().getContentAsString();
        VicReturn vr = TestUtil.convertStringVicReturn(toString);
        assertNotNull(toString);

    }

    @Test
    @Transactional
    public void getOne() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        restMockMvc.perform(get("/api/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").value(DEAFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExisting() throws Exception {
        restMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        int databaseSizeBeforeUpdate = findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).orElse(null);
        updatedBook.setName("NEW NAME");
        restMockMvc.perform(put("/api/books/" + book.getId())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
                .andExpect(status().isOk());

        List<Book> bookList = findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book Book = bookList.get(bookList.size() - 1);
        assertThat(Book.getName()).isEqualTo("NEW NAME");
    }

    @Test
    @Transactional
    public void updateNonExisting() throws Exception {
        int databaseSizeBeforeUpdate = findAll().size();

        restMockMvc.perform(put("/api/books")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isCreated());

        // Validate the Cargo in the database
        List<Book> bookList = findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCargo() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        int databaseSizeBeforeDelete = findAll().size();

        // Get the book
        restMockMvc.perform(delete("/api/books/{id}", book.getId())
                        .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Book> bookList = findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();

        Book book2 = new Book();

        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId("2L");
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @Transactional
    public void getHQL() throws Exception {
        // Initialize the database
        restMockMvc.perform(post("/api/books")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(this.book)))
                .andExpect(status().isCreated());

        restMockMvc.perform(get("/api/books?hql=name like '%'&sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.values.[*].id").value(hasItem(this.book.getId())))
                .andExpect(jsonPath("$.values.[*].name").value(hasItem(DEAFAULT_NAME)));

    }

    @Test
    @Transactional
    public void getVQuery() throws Exception {
        // Initialize the database
        restMockMvc.perform(post("/api/books")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(this.book)))
                .andExpect(status().isCreated());

        VicQuery v = new VicQuery();
        VQuery vQuery = new VQuery(new Criteria("name", ComparisonOperator.CONTAINS, "The Book"))
                .or(new Criteria("name", ComparisonOperator.CONTAINS, "books"));
        v.setQuery(vQuery);
        ResultActions perform = restMockMvc.perform(post("/api/books/vquery")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(v)));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.values.[*].id").value(hasItem(book.getId())))
                .andExpect(jsonPath("$.values.[*].name").value(hasItem(DEAFAULT_NAME)));
    }


}
