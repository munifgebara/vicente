/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 02:07:54 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.LogicalOperator;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.*;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.dto.LoginResponseDto;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author GeradorVicente
 */
@Service
public class TokenService extends BaseService<Token> {

    private final UserService userService;
    private final GroupService groupService;
    private final OrganizationService organizationService;

    public TokenService(VicRepository<Token> repository, OrganizationService organizationService, GroupService groupService, UserService userService) {
        super(repository);
        this.organizationService = organizationService;
        this.groupService = groupService;
        this.userService = userService;
    }

    public LoginResponseDto logaGoogle(String token) {
        LoginResponseDto r = new LoginResponseDto();
        Map verify = GoogleToken.verify(token);
        String email = (String) verify.get("email");
        if (email == null) {
            r.message = "Usuário não encontrado.";
            return r;
        }
        List<User> findByHql = findUsersByEmail(email);

        if (findByHql.size() == 0) {
            VicThreadScope.gi.set("GOOGLE");
            VicThreadScope.ui.set("GOOGLE");
            VicThreadScope.oi.set("GOOGLE.");
            VicThreadScope.defaultRights.set(RightsHelper.OWNER_ALL + RightsHelper.GROUP_READ_UPDATE + RightsHelper.OTHER_READ);
            User u = new User();
            u.setLogin((String) verify.get("email"));
            u.setPassword(PasswordGenerator.generate("123")); //TODO MUDAR
            Group g0 = groupService.createGroupByEmail(email);
            String fa = verify.get("family_name").toString();
            Organization o1 = organizationService.createOrganizationByEmail(fa);

            u.setGroups(new HashSet<>());
            u.getGroups().add(g0);
            u.setOrganization(o1);

            u = userService.save(u);
            r.message = "Usuário criado, Login OK";
            r.ok = true;
            r.token = criaToken(u);
        } else if (findByHql.size() == 1) {
            r.message = "Login OK";
            r.ok = true;
            r.token = criaToken(findByHql.get(0));
        } else {
            r.message = "Multiplos Usuários";
        }
        return r;
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByEmail(String email) {
        VQuery vQuery = new VQuery(new Criteria("login", ComparisonOperator.EQUAL, email.trim()));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        return userService.findByHqlNoTenancy(query);
    }

    public LoginResponseDto loga(LoginDto login) {
        LoginResponseDto r = new LoginResponseDto();

        VQuery vQuery = new VQuery(LogicalOperator.AND, new Criteria(),
                Collections.singletonList(new VQuery(new Criteria("login", ComparisonOperator.EQUAL, login.login.trim()))));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        List<User> findByHql = userService.findByHqlNoTenancy(query);
        if (findByHql.size() == 0) {
            r.message = "Usuário não encontrado.";
            return r;
        } else if (findByHql.size() == 1) {
            return createTokenToExistentUser(login, findByHql.get(0));
        } else {
            r.message = "Multiplos Usuários.";
        }
        return r;
    }

    @Transactional
    public Token criaToken(User user) {
        Token t = newEntity();
        t.setValue(t.getId());
        t.setUser(user);
        return save(t);
    }

    public LoginResponseDto logout() {
        Token tok = repository.load(VicThreadScope.token.get());
        LoginResponseDto lr = new LoginResponseDto();
        lr.code = 0;
        lr.message = "Volte sempre";
        lr.ok = true;
        lr.token = null;
        return lr;
    }

    public Token findUserByToken(String tokenValue) {
        return loadNoTenancy(tokenValue);
    }

    @Transactional
    public LoginResponseDto sigin(LoginDto login) {
        VicThreadScope.ui.set(login.login);
        VicThreadScope.gi.set(login.login.replaceAll("\\.", "_"));
        VicThreadScope.cg.set(login.login.replaceAll("\\.", "_"));
        VicThreadScope.oi.set(login.login.replaceAll("\\.", "_") + ".");
        VicThreadScope.defaultRights.set(RightsHelper.OWNER_READ);
        return createAndLogin(login);
    }

    @Transactional
    public LoginResponseDto createAndLogin(LoginDto login) {
        User userByLogin = createUserByLogin(login);
        return createTokenToExistentUser(login, userByLogin);
    }

    public User createUserByLogin(LoginDto login) {
        List<User> usersByEmail = findUsersByEmail(login.login);
        if (usersByEmail.size() > 0) {
            return usersByEmail.get(0);
        }
        User u = new User();
        u.setLogin(login.login);
        Group group = groupService.createGroupByEmail(login.login);
        Organization organization = organizationService.createOrganizationByEmail(login.login);
        u.setPassword(PasswordGenerator.generate(login.password));
        u.setGroups(Sets.newHashSet(group));
        u.setOrganization(organization);
        u = userService.save(u);
        return u;
    }

    public LoginResponseDto createTokenToExistentUser(LoginDto login, User user) {
        LoginResponseDto r = new LoginResponseDto();
        if (!PasswordGenerator.validate(login.password, user.getPassword())) {
            r.message = "Senha inválida.";
            return r;
        }
        r.message = "Login OK";
        r.ok = true;
        r.token = criaToken(user);
        return r;
    }
}
