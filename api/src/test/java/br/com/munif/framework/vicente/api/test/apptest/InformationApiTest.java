/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.api.test.apptest.api.InformationApi;
import br.com.munif.framework.vicente.api.test.apptest.domain.Information;
import br.com.munif.framework.vicente.api.test.apptest.repository.InformationRepository;
import br.com.munif.framework.vicente.api.test.apptest.service.InformationService;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InformationApp.class)
public class InformationApiTest {

    @Autowired
    private InformationRepository repository;

    @Autowired
    private InformationService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    //    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final InformationApi api = new InformationApi(service);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(api)
                //                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();

        otherOwnership();
        VicThreadScope.defaultRights.set(RightsHelper.OWNER_READ);
        repository.save(new Information("This is a private"));

        VicThreadScope.defaultRights.set(RightsHelper.ALL_READ);
        repository.save(new Information("This is a public information"));

        groupOwnership();
        VicThreadScope.defaultRights.set(RightsHelper.OWNER_READ | RightsHelper.GROUP_READ);
        repository.save(new Information("Group Information"));
        myOwnership();
        VicThreadScope.defaultRights.set(RightsHelper.OWNER_ALL);
        repository.save(new Information("Owner Information"));
    }

    public void otherOwnership() {
        VicThreadScope.gi.set("C1,G1,G2");
        VicThreadScope.ui.set("OTHER_USER");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        VicThreadScope.cg.set("GX");
    }

    public void groupOwnership() {
        VicThreadScope.gi.set("C1,G1,G2");
        VicThreadScope.ui.set("GROUP_USER");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        VicThreadScope.cg.set("G1");
    }

    public void myOwnership() {
        VicThreadScope.gi.set("C1,G1,G2");
        VicThreadScope.ui.set("THIS_USER");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        VicThreadScope.cg.set("G1");
    }

    public String describeInformation(Information i) {
        return "{" + i.getId() + "," + i.getInfo() + "," + i.getRights() + "," + i.r() + "}";
    }

    public String describeInformations(List<Information> informations) {
        String is = "[";
        for (Information i : informations) {
            is += describeInformation(i) + " ";
        }
        is += "]";
        return is;
    }

    @Test
    @Transactional
    public void T1() throws Exception {
        myOwnership();
        List<Information> all = repository.findAll();
        assertNotEquals(0, all.size());
    }

    @Test
    @Transactional
    public void T2() throws Exception {
        myOwnership();

        MvcResult r = restMockMvc.perform(get("/api/information")).andReturn();
        String contentAsString = r.getResponse().getContentAsString();

        assertNotEquals("[]", contentAsString);

    }

    @Test
    @Transactional
    public void T3() throws Exception {
        myOwnership();
        List<Information> all = repository.findAll();

        restMockMvc.perform(delete("/api/information/" + all.get(1).getId())
                        .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void T4() throws Exception {
        myOwnership();
        List<Information> all = repository.findAllNoTenancy();
        restMockMvc.perform(get("/api/information/" + all.get(0).getId())
                        .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void T5() throws Exception {
        myOwnership();
        List<Information> all = repository.findAll();
        restMockMvc.perform(get("/api/information/" + all.get(1).getId())
                        .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void T6() throws Exception {
        myOwnership();
        List<Information> all = repository.findAll();
        restMockMvc.perform(delete("/api/information/" + all.get(2).getId())
                        .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

}
