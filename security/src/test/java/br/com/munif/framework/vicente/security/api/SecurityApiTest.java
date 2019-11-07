/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.errors.ExceptionTranslator;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.security.SecurityApp;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.seed.SeedSecurity;
import br.com.munif.framework.vicente.security.service.TokenService;
import br.com.munif.framework.vicente.security.service.UserService;
import static org.junit.Assert.*;
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
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityApp.class)
public class SecurityApiTest {

    public static final String DEAFAULT_NAME = "The Book";

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private SeedSecurity seedSecurity;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    private Token token;

    @Before
    public void setup() {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);

        this.restMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        seedSecurity.seedSecurity();
    }

    @Test
    public void loginByPassword() throws Exception {
        System.out.println("");
        ResultActions tokenRequest = restMockMvc.perform(post("/api/token/login/bypassword")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new LoginDto("admin@munif.com.br", "qwe123"))));
        tokenRequest.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.ok").value(Boolean.TRUE))
                .andExpect(jsonPath("$.message").value("Login OK"))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.token.user.login").value("admin@munif.com.br"))
                .andExpect(jsonPath("$.token.user.password").doesNotExist());
        Map<String, Object> tokenMap = TestUtil.convertStringToMap(tokenRequest.andReturn().getResponse().getContentAsString());
        Map token = (Map) tokenMap.get("token");
        Token value = tokenService.load((String) token.get("value"));
        assertNotNull(value);
    }

}
