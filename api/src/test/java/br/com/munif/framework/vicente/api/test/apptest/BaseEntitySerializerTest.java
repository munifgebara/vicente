/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.api.test.apptest.api.BookApi;
import br.com.munif.framework.vicente.api.test.apptest.api.PessoaGenericaApi;
import br.com.munif.framework.vicente.api.test.apptest.api.PontoApi;
import br.com.munif.framework.vicente.api.test.apptest.api.SalarioApi;
import br.com.munif.framework.vicente.api.test.apptest.domain.Book;
import br.com.munif.framework.vicente.api.test.apptest.domain.PessoaGenerica;
import br.com.munif.framework.vicente.api.test.apptest.domain.Ponto;
import br.com.munif.framework.vicente.api.test.apptest.domain.Salario;
import br.com.munif.framework.vicente.api.test.apptest.repository.BookRepository;
import br.com.munif.framework.vicente.api.test.apptest.repository.PessoaGenericaRepository;
import br.com.munif.framework.vicente.api.test.apptest.repository.PontoRepository;
import br.com.munif.framework.vicente.api.test.apptest.repository.SalarioRepository;
import br.com.munif.framework.vicente.api.test.apptest.service.BookService;
import br.com.munif.framework.vicente.api.test.apptest.service.PessoaGenericaService;
import br.com.munif.framework.vicente.api.test.apptest.service.PontoService;
import br.com.munif.framework.vicente.api.test.apptest.service.SalarioService;
import br.com.munif.framework.vicente.core.VicThreadScope;
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
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InformationApp.class)
public class BaseEntitySerializerTest {

    public static final String DEAFAULT_NAME = "The Book";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private SalarioRepository salarioRepository;

    @Autowired
    private SalarioService salarioService;

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private PontoService pontoService;

    @Autowired
    private PessoaGenericaRepository pessoaGenericaRepository;

    @Autowired
    private PessoaGenericaService pessoaGenericaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvcBook;
    private MockMvc restMockMvcPessoa;
    private MockMvc restMockMvcPonto;
    private MockMvc restMockMvcSalario;

    private Book book;
    private Ponto ponto;
    private Salario salario;
    private PessoaGenerica pessoa;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);

        final BookApi bookAPi = new BookApi(bookService);
        this.restMockMvcBook = MockMvcBuilders.standaloneSetup(bookAPi)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
//
        final PessoaGenericaApi pessoaGenericaApi = new PessoaGenericaApi(pessoaGenericaService);
        this.restMockMvcPessoa = MockMvcBuilders.standaloneSetup(pessoaGenericaApi)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

        final PontoApi pontoApi = new PontoApi(pontoService);
        this.restMockMvcPonto = MockMvcBuilders.standaloneSetup(pontoApi)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

        final SalarioApi salarioApi = new SalarioApi(salarioService);
        this.restMockMvcSalario = MockMvcBuilders.standaloneSetup(salarioApi)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

    }

    public static Book createBook() {
        Book book = new Book();
        book.setName(DEAFAULT_NAME);
        return book;
    }

    public static PessoaGenerica createPessoa() {
        PessoaGenerica pessoa = new PessoaGenerica();
        pessoa.setNome("Willian");
        return pessoa;
    }

    public static Salario createSalario() {
        Salario salario = new Salario();
        salario.setNome("Willian");
        salario.setValor(BigDecimal.TEN);
        return salario;
    }

    public static Ponto createPonto() {
        Ponto ponto = new Ponto();
        ponto.setNome("Willian");
        ponto.setEntrada(ZonedDateTime.now());
        ponto.setSaida(ZonedDateTime.now());
        return ponto;
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
        book = createBook();
        pessoa = createPessoa();
        salario = createSalario();
        ponto = createPonto();
    }


    @Test
    @Transactional
    public void getOne() throws Exception {
        // Initialize the database
        restMockMvcBook.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(this.book)))
                .andExpect(status().isCreated());

        restMockMvcPonto.perform(post("/api/ponto")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(this.ponto)))
                .andExpect(status().isCreated());

        restMockMvcPessoa.perform(post("/api/pessoagenerica")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(this.pessoa)))
                .andExpect(status().isCreated());

        restMockMvcSalario.perform(post("/api/salario")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(this.salario)))
                .andExpect(status().isCreated());

        ResultActions perform = restMockMvcBook.perform(get("/api/books/{id}", book.getId()).param("fields", "id"));
        ResultActions perform1 = restMockMvcSalario.perform(get("/api/salario/{id}", salario.getId()).param("fields", "id"));
        ResultActions perform2 = restMockMvcPessoa.perform(get("/api/pessoagenerica/{id}", pessoa.getId()).param("fields", "id"));
        ResultActions perform3 = restMockMvcPonto.perform(get("/api/ponto/{id}", ponto.getId()).param("fields", "id"));

        perform.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.name").doesNotExist());
        perform1.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(salario.getId()))
                .andExpect(jsonPath("$.name").doesNotExist());
        perform2.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(pessoa.getId()))
                .andExpect(jsonPath("$.name").doesNotExist());
        perform3.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(ponto.getId()))
                .andExpect(jsonPath("$.name").doesNotExist());

    }


}
