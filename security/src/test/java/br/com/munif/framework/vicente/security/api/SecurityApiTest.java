/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.errors.ErrorConstants;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.VicThreadScopeOptions;
import br.com.munif.framework.vicente.security.SecurityApp;
import br.com.munif.framework.vicente.security.domain.Group;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.domain.profile.*;
import br.com.munif.framework.vicente.security.domain.dto.LoginDto;
import br.com.munif.framework.vicente.security.domain.dto.PrivilegesAssignmentDto;
import br.com.munif.framework.vicente.security.seed.SeedSecurity;
import br.com.munif.framework.vicente.security.service.TokenService;
import br.com.munif.framework.vicente.security.service.UserService;
import br.com.munif.framework.vicente.security.service.profile.OperationFilterService;
import br.com.munif.framework.vicente.security.service.profile.OperationService;
import br.com.munif.framework.vicente.security.service.profile.ProfileService;
import br.com.munif.framework.vicente.security.service.profile.SoftwareService;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityApp.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityApiTest {

    public static final String DEAFAULT_NAME = "The Book";

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Environment environment;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private SeedSecurity seedSecurity;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OperationFilterService operationFilterService;
    @Autowired
    private ProfileService profileService;

    private MockMvc restMockMvc;
    private ResultActions tokenRequestWillian;
    private ResultActions tokenRequestJose;
    private ResultActions tokenRequestLucas;
    private ResultActions tokenRequestAdmin;

    private Token tokenWillian;
    private Token tokenJose;
    private Token tokenLucas;
    private Token tokenAdmin;

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

        tokenRequestAdmin = restMockMvc.perform(post("/api/token/sigin")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new LoginDto("admin@vicente.com.br", "qwe123"))));
        Map<String, Object> tokenMapAdmin = TestUtil.convertStringToMap(tokenRequestAdmin.andReturn().getResponse().getContentAsString());
        Map tokenAdmin = (Map) tokenMapAdmin.get("token");
        this.tokenAdmin = tokenService.load((String) tokenAdmin.get("value"));

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
    public void testBloginByPasswordSeederAdmin() throws Exception {
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
    public void testCsiginNewUser() throws Exception {
        tokenRequestWillian.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.ok").value(Boolean.TRUE))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.token.user.login").value("willianmarquesfreire@gmail.com.br"))
                .andExpect(jsonPath("$.token.user.password").doesNotExist());
        assertNotNull(tokenWillian);
    }

    @Test
    public void testDremoveOrganizationFromNewUser() throws Exception {
        ResultActions tokenRequest = restMockMvc.perform(delete("/api/organization/" + Objects.requireNonNull(tokenWillian.getUser().getOrganizations().stream().findFirst().orElse(null)).getId())
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        tokenRequest.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));
    }

    @Test
    public void testEremoveGroupFromNewUser() throws Exception {
        ResultActions tokenRequest = restMockMvc.perform(delete("/api/group/" + tokenWillian.getUser().getFirstGroup().getId())
                .header("Authorization", tokenWillian.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        tokenRequest.andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_NOT_ALLOWED));
    }

    @Test
    public void testFcreateAndRemoveNewGroupFromNewUser() throws Exception {
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
    public void testGpermissionsWillianJoseAtNewGroup() throws Exception {
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
    public void testHpermissionsOfWillianAtJoseMainGroup() throws Exception {
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
    public void testIcreateRepeatedGroupToJose() throws Exception {
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
    public void testFassignGroupsFromWillianToJose() throws Exception {
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

    @Test
    public void testKrequestAnotherOnRequest() throws Exception {
        Software software = new Software("Mine", Sets.newHashSet(
                Arrays.asList(new Operation("GroupApi_load"),
                        new Operation("UserApi_teste2"))
        ));

        ResultActions createRequestSoftware = restMockMvc.perform(post("/api/software")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(software))
                .header("Authorization", tokenAdmin.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        String reqSoftware = createRequestSoftware.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseSoftware = TestUtil.convertStringToMap(reqSoftware);

        User user = tokenLucas.getUser();
        Profile profile = new Profile("Profile teste", user, Sets.newHashSet(
                new OperationFilter(operationService.findOne(software.getOperation(0).getId()), OperationType.ALLOW),
                new OperationFilter(operationService.findOne(software.getOperation(1).getId()), OperationType.ALLOW)
        ));

        ResultActions createRequestProfile = restMockMvc.perform(post("/api/profile")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profile))
                .header("Authorization", tokenAdmin.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        String reqProfile = createRequestProfile.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseProfile = TestUtil.convertStringToMap(reqProfile);

        Group group = new Group("GROUP LUCAS 2", "GROUP_WILLIAN2");
        String authorization = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(group))
                .header("Authorization", tokenLucas.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)).andReturn().getResponse().getContentAsString();
        Map<String, Object> newGroup = TestUtil.convertStringToMap(authorization);

        List<OperationFilter> allNoTenancy = operationFilterService.findAllNoTenancy();
        for (OperationFilter operationFilter : allNoTenancy) {
            operationFilter.setActions(Arrays.asList(
                    new ForwardRequest("http://127roskjdfaskldnomeerradonaoexistenteup-wmfteste/" + newGroup.get("id"), HttpMethod.PUT)
            ));
            operationFilterService.save(operationFilter);
        }

        VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.setValue(true);

        restMockMvc.perform(get("/api/group/" + newGroup.get("id"))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .header("Authorization", tokenLucas.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value("error.badRequest"));

        VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.setValue(false);

        restMockMvc.perform(get("/api/group/" + newGroup.get("id"))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .header("Authorization", tokenLucas.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").doesNotExist());

    }

    @Test
    public void testLoperationsAllowNotAllow() throws Exception {
        Software software = new Software("Mine", Sets.newHashSet(
                Arrays.asList(new Operation("GroupApi_save"),
                        new Operation("UserApi_teste2"))
        ));

        ResultActions createRequestSoftware = restMockMvc.perform(post("/api/software")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(software))
                .header("Authorization", tokenAdmin.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        String reqSoftware = createRequestSoftware.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseSoftware = TestUtil.convertStringToMap(reqSoftware);

        User user = tokenJose.getUser();
        Profile profile = new Profile("Profile teste", user, Sets.newHashSet(
                new OperationFilter(operationService.findOne(software.getOperation(0).getId()), OperationType.DENY),
                new OperationFilter(operationService.findOne(software.getOperation(1).getId()), OperationType.DENY)
        ));

        ResultActions createRequestProfile = restMockMvc.perform(post("/api/profile")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profile))
                .header("Authorization", tokenAdmin.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8));
        String reqProfile = createRequestProfile.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseProfile = TestUtil.convertStringToMap(reqProfile);

        ResultActions perform = restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .header("Authorization", tokenJose.getValue())
                .content(TestUtil.convertObjectToJsonBytes(new Group("aaaaaaa1", "aaaaaaa1")))
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value("error.notAllowed"));

        List<OperationFilter> allNoTenancy = operationFilterService.findAllNoTenancy();
        for (OperationFilter operationFilter : allNoTenancy) {
            OperationFilter op = operationFilterService.loadNoTenancy(operationFilter.getId());
            op.setOperationType(OperationType.ALLOW);
            op = operationFilterService.save(op);
        }

        restMockMvc.perform(post("/api/group")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new Group("aaaaaaa3", "aaaaaaa3")))
                .header("Authorization", tokenJose.getValue())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").doesNotExist());

        for (OperationFilter operationFilter : allNoTenancy) {
            OperationFilter op = operationFilterService.loadNoTenancy(operationFilter.getId());
            op.setActions(Collections.singletonList(
                    new ForwardRequest("http://localhost:3000", HttpMethod.POST)
            ));
            op = operationFilterService.save(op);
        }
//        VicThreadScopeOptions.ENABLE_FORWARD_REQUEST_EXCEPTION.setValue(true);
//        ResultActions authorization = restMockMvc.perform(post("/api/group")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .header("Authorization", tokenJose.getValue())
//                .content(TestUtil.convertObjectToJsonBytes(new Group("aaaaaaa2", "aaaaaaa2")))
//                .contentType(TestUtil.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$.message").doesNotExist());
//        String contentAsString = authorization.andReturn().getResponse().getContentAsString();

    }

}
