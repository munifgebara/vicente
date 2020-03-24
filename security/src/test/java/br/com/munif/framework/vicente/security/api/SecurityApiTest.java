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
import br.com.munif.framework.vicente.security.dto.PrivilegesAssignmentDto;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.doesNotHave;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;
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
    private ResultActions tokenRequestLucas;

    private Token tokenWillian;
    private Token tokenJose;
    private Token tokenLucas;

    @Before
    public void setup() throws Exception {
        VicThreadScope.gi.set(null);
        VicThreadScope.ui.set(null);
        VicThreadScope.oi.set(null);
        VicThreadScope.ip.set("127.0.0.1");
        VicThreadScope.cg.set(null);
        VicThreadScope.token.set(null);
        MockitoAnnotations.initMocks(this);

        this.restMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        seedSecurity.seedSecurity();

        tokenRequestWillian = restMockMvc.perform(post("/api/token/sigin")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new LoginDto("willianmarquesfreire@gmail.com.br", "1234"))));
        Map<String, Object> tokenMapWillian = TestUtil.convertStringToMap(tokenRequestWillian.andReturn().getResponse().getContentAsString());
        Map tokenWillian = (Map) tokenMapWillian.get("token");
        this.tokenWillian = tokenService.load((String) tokenWillian.get("value"));

        tokenRequestJose = restMockMvc.perform(post("/api/token/sigin")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new LoginDto("jose@gmail.com.br", "1234"))));
        Map<String, Object> tokenMapJose = TestUtil.convertStringToMap(tokenRequestJose.andReturn().getResponse().getContentAsString());
        Map tokenJose = (Map) tokenMapJose.get("token");
        this.tokenJose = tokenService.load((String) tokenJose.get("value"));

        tokenRequestLucas = restMockMvc.perform(post("/api/token/sigin")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new LoginDto("lucas@gmail.com.br", "1234"))));
        Map<String, Object> tokenMapLucas = TestUtil.convertStringToMap(tokenRequestLucas.andReturn().getResponse().getContentAsString());
        Map tokenLucas = (Map) tokenMapLucas.get("token");
        this.tokenLucas = tokenService.load((String) tokenLucas.get("value"));
    }

    @Test
    public void loginByPasswordSeederAdmin() throws Exception {
        System.out.println("");
        ResultActions tokenRequest = restMockMvc.perform(post("/api/token/login/bypassword")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new LoginDto("admin@vicente.com.br", "qwe123"))));
        tokenRequest.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.ok").value(Boolean.TRUE))
                .andExpect(jsonPath("$.message").value("Login OK"))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.token.user.login").value("admin@vicente.com.br"))
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
                .header("Authorization", tokenLucas.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        req1.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));

        ResultActions req2 = restMockMvc.perform(delete("/api/group/" + tokenLucas.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        req2.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));

        ResultActions req3 = restMockMvc.perform(put("/api/group/" + tokenLucas.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tokenLucas.getUser().getFirstGroup())));
        req3.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", "NEW NAME");
        ResultActions req4 = restMockMvc.perform(patch("/api/group/" + tokenLucas.getUser().getFirstGroup().getId())
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(objectObjectHashMap)));
        req4.andExpect(status().isNoContent());
    }

    @Test
    public void createRepeatedGroupToJose() throws Exception {
        Group group = new Group("GROUP LUCAS", "GROUP_LUCAS");
        ResultActions createRequest = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(group))
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        createRequest.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(group.getName()))
                .andExpect(jsonPath("$.code").value(group.getCode()));

        ResultActions getAllRequest = restMockMvc.perform(get("/api/group")
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        List<Map> getAllWillian = (List<Map>) TestUtil.convertStringToMap(getAllRequest.andReturn().getResponse().getContentAsString()).get("values");

        getAllRequest.andExpect(jsonPath("$.values.[*].name").value(hasItem("GROUP LUCAS")))
                .andExpect(jsonPath("$.values.[*].name").value(hasItem(tokenWillian.getUser().getFirstGroup().getName())));

        Group duplicatedGroup = new Group(tokenWillian.getUser().getFirstGroup().getName(), tokenWillian.getUser().getFirstGroup().getCode());
        ResultActions createRequest2 = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(duplicatedGroup))
                .header("Authorization", tokenLucas.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        createRequest2.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(tokenWillian.getUser().getFirstGroup().getName()))
                .andExpect(jsonPath("$.code").value(tokenWillian.getUser().getFirstGroup().getCode()));

        ResultActions getAllRequest2 = restMockMvc.perform(get("/api/group")
                .header("Authorization", tokenLucas.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        List<Map> getAllJose = (List<Map>) TestUtil.convertStringToMap(getAllRequest2.andReturn().getResponse().getContentAsString()).get("values");

        assertFalse(getAllJose.stream().anyMatch((Map map) -> getAllWillian.stream().anyMatch((Map sm) -> map.get("id").equals(sm.get("id")))));
    }

    @Test
    public void assignGroupsFromWillianToJose() throws Exception {
        Group group = new Group("GROUP WILLIAN 2", "GROUP_WILLIAN2");
        ResultActions createRequest = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(group))
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        createRequest.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(group.getName()))
                .andExpect(jsonPath("$.code").value(group.getCode()));

        ResultActions getAllRequest = restMockMvc.perform(get("/api/group")
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        List<Map> getAllWillian = (List<Map>) TestUtil.convertStringToMap(getAllRequest.andReturn().getResponse().getContentAsString()).get("values");

        getAllRequest.andExpect(jsonPath("$.values.[*].name").value(hasItem("GROUP WILLIAN 2")))
                .andExpect(jsonPath("$.values.[*].name").value(hasItem(tokenWillian.getUser().getFirstGroup().getName())));


        ResultActions reqPrivileges = restMockMvc.perform(post("/api/user/assign-privileges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new PrivilegesAssignmentDto(tokenWillian.getUser().getFirstGroup().getCode(), tokenJose.getUser().getLogin())))
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));


        ResultActions getAllRequest2 = restMockMvc.perform(get("/api/group")
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));

        List<Map> getAllJose = (List<Map>) TestUtil.convertStringToMap(getAllRequest2.andReturn().getResponse().getContentAsString()).get("values");
        List<Map> collect = getAllJose.stream().filter((Map map) -> getAllWillian.stream().anyMatch((Map sm) -> map.get("id").equals(sm.get("id")))).collect(Collectors.toList());
        Map map = collect.get(0);
        assertEquals(group.getName(), map.get("name"));
        assertEquals(group.getCode(), map.get("code"));
    }

}
