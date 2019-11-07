/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.errors.ErrorConstants;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.security.SecurityApp;
import br.com.munif.framework.vicente.security.domain.Group;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.seed.SeedSecurity;
import br.com.munif.framework.vicente.security.service.TokenService;
import br.com.munif.framework.vicente.security.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.doesNotHave;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private MockMvc restMockMvc;
    private ResultActions tokenRequestWillian;
    private ResultActions tokenRequestJose;

    private Token tokenWillian;
    private Token tokenJose;

    @Before
    public void setup() throws Exception {
        VicThreadScope.gi.set("GRUPO");
        VicThreadScope.ui.set("USUARIO");
        VicThreadScope.oi.set("1.");
        VicThreadScope.ip.set("127.0.0.1");
        MockitoAnnotations.initMocks(this);

        this.restMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        seedSecurity.seedSecurity();

        if (tokenRequestWillian == null) {
            tokenRequestWillian = restMockMvc.perform(post("/api/token/sigin")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(new LoginDto("willianmarquesfreire@gmail.com.br", "1234"))));
            Map<String, Object> tokenMapWillian = TestUtil.convertStringToMap(tokenRequestWillian.andReturn().getResponse().getContentAsString());
            Map tokenWillian = (Map) tokenMapWillian.get("token");
            this.tokenWillian = tokenService.load((String) tokenWillian.get("value"));
        }
        if (tokenRequestJose == null) {
            tokenRequestJose = restMockMvc.perform(post("/api/token/sigin")
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(new LoginDto("jose@gmail.com.br", "1234"))));
            Map<String, Object> tokenMapJose = TestUtil.convertStringToMap(tokenRequestJose.andReturn().getResponse().getContentAsString());
            Map tokenJose = (Map) tokenMapJose.get("token");
            this.tokenJose = tokenService.load((String) tokenJose.get("value"));
        }
    }

    @Test
    public void loginByPasswordSeederAdmin() throws Exception {
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

    @Test
    public void siginNewUser() throws Exception {
        tokenRequestWillian.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.ok").value(Boolean.TRUE))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.token.user.login").value("willianmarquesfreire@gmail.com.br"))
                .andExpect(jsonPath("$.token.user.password").doesNotExist());
        assertNotNull(tokenWillian);
    }

    @Test
    public void removeOrganizationFromNewUser() throws Exception {
        ResultActions tokenRequest = restMockMvc.perform(delete("/api/organization/" + tokenWillian.getUser().getOrganization().getId())
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        tokenRequest.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));
    }

    @Test
    public void removeGroupFromNewUser() throws Exception {
        ResultActions tokenRequest = restMockMvc.perform(delete("/api/group/" + tokenWillian.getUser().getFirstGroup().getId())
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        tokenRequest.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));
    }

    @Test
    public void createAndRemoveNewGroupFromNewUser() throws Exception {
        Group group = new Group("MY NEW GROUP", "MY_NEW_GROUP");
        ResultActions createRequest = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(group))
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        createRequest.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(group.getName()))
                .andExpect(jsonPath("$.code").value(group.getCode()));

        Map<String, Object> result = TestUtil.convertStringToMap(createRequest.andReturn().getResponse().getContentAsString());
        String id = (String) result.get("id");
        ResultActions deleteRequest = restMockMvc.perform(delete("/api/group/" + id)
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        deleteRequest.andExpect(status().isNoContent());
    }

    @Test
    public void permissionsWillianJoseAtNewGroup() throws Exception {
        Group group = new Group("NEW WILLIAN GROUP", "NEW_WILLIAN_GROUP");
        ResultActions createRequest = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(group))
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        createRequest.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(group.getName()))
                .andExpect(jsonPath("$.code").value(group.getCode()));

        Map<String, Object> result = TestUtil.convertStringToMap(createRequest.andReturn().getResponse().getContentAsString());
        String id = (String) result.get("id");
        ResultActions deleteRequest = restMockMvc.perform(delete("/api/group/" + id)
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        deleteRequest.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));
    }

    @Test
    public void permissionsOfWillianAtJoseMainGroup() throws Exception {
        ResultActions req1 = restMockMvc.perform(get("/api/group/" + tokenJose.getUser().getFirstGroup().getId())
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        req1.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));

        ResultActions req2 = restMockMvc.perform(delete("/api/group/" + tokenWillian.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        req2.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));

        ResultActions req3 = restMockMvc.perform(put("/api/group/" + tokenWillian.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tokenWillian.getUser().getFirstGroup())));
        req3.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", "NEW NAME");
        ResultActions req4 = restMockMvc.perform(patch("/api/group/" + tokenWillian.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(objectObjectHashMap)));
        req4.andExpect(status().isNoContent());

        ResultActions req41 = restMockMvc.perform(get("/api/group/" + tokenJose.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        req41.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(tokenJose.getUser().getFirstGroup().getName()));
    }

    @Test
    public void createRepeatedGroupToJose() throws Exception {
        Group group = new Group("GRUPO DUPLICADO", "GRUPO_DUPLICADO");
        ResultActions createRequest = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(group))
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        createRequest.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(group.getName()))
                .andExpect(jsonPath("$.code").value(group.getCode()));

        Map<String, Object> result = TestUtil.convertStringToMap(createRequest.andReturn().getResponse().getContentAsString());
        String id = (String) result.get("id");
        ResultActions deleteRequest = restMockMvc.perform(delete("/api/group/" + id)
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        deleteRequest.andExpect(status().isNoContent());
    }

}
