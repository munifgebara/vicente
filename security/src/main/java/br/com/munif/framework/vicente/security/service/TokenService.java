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
import br.com.munif.framework.vicente.security.repository.GroupRepository;
import br.com.munif.framework.vicente.security.repository.OrganizationRepository;
import br.com.munif.framework.vicente.security.repository.UserRepository;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GeradorVicente
 */
@Service
public class TokenService extends BaseService<Token> {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final OrganizationRepository organizationRepository;

    public TokenService(VicRepository<Token> repository, OrganizationRepository organizationRepository, GroupRepository groupRepository, UserRepository userRepository, UserService userService) {
        super(repository);
        this.organizationRepository = organizationRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public LoginResponseDto logaGoogle(String token) {
        LoginResponseDto r = new LoginResponseDto();
        Map verify = GoogleToken.verify(token);
        String email = (String) verify.get("email");
        if (email == null) {
            r.message = "Erro em Usuário ou senha";
            return r;
        }
        VQuery vQuery = new VQuery(new Criteria("login", ComparisonOperator.EQUAL, email.trim()));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        List<User> findByHql = userService.findByHql(query);

        if (findByHql.size() == 0) {
            VicThreadScope.gi.set("GOOGLE");
            VicThreadScope.ui.set("GOOGLE");
            VicThreadScope.oi.set("GOOGLE.");
            VicThreadScope.defaultRights.set(RightsHelper.OWNER_ALL + RightsHelper.GROUP_READ_UPDATE + RightsHelper.OTHER_READ);
            User u = new User();

            u.setLogin((String) verify.get("email"));
            u.setPassword(PasswordGenerator.generate("123")); //TODO MUDAR

            Group g0 = new Group();

            g0.setCode(email.replaceAll("\\.", "_"));
            g0.setName(email);
            groupRepository.save(g0);

            Organization o1 = new Organization();

            String fa = verify.get("family_name").toString();
            o1.setCode(fa.replaceAll(" ", "_"));
            o1.setName(fa);
            organizationRepository.save(o1);

            u.setGroups(new HashSet<>());
            u.getGroups().add(g0);
            u.setOrganization(o1);

            u = userRepository.save(u);
            r.message = "Usuário criado, Login OK";
            r.ok = true;
            r.token = criaToken(u);
        } else if (findByHql.size() == 1) {
            r.message = "Login OK";
            r.ok = true;
            r.token = criaToken(findByHql.get(0));
        } else if (findByHql.size() > 0) {
            r.message = "Multiplos Usuários";
        }
        return r;
    }

    public LoginResponseDto loga(LoginDto login) {
        LoginResponseDto r = new LoginResponseDto();

        VQuery vQuery = new VQuery(LogicalOperator.AND, new Criteria(),
                Collections.singletonList(new VQuery(new Criteria("login", ComparisonOperator.EQUAL, login.login.trim()))));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        List<User> findByHql = userService.findByHql(query);
        if (findByHql.size() == 0) {
            r.message = "Usuário não encontrado.";
            return r;
        } else if (findByHql.size() == 1) {
            if (!PasswordGenerator.validate(login.password, findByHql.get(0).getPassword())) {
                r.message = "Senha inválida.";
                return r;
            }
            r.message = "Login OK";
            r.ok = true;
            r.token = criaToken(findByHql.get(0));
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

}
