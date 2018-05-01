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
import br.com.munif.framework.vicente.domain.BaseEntityHelper;
import br.com.munif.framework.vicente.security.domain.Grupo;
import br.com.munif.framework.vicente.security.domain.Organizacao;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.Usuario;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.dto.LoginRespostaDto;
import br.com.munif.framework.vicente.security.repository.GrupoRepository;
import br.com.munif.framework.vicente.security.repository.OrganizacaoRepository;
import br.com.munif.framework.vicente.security.repository.UsuarioRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GeradorVicente
 */
@Service
public class TokenService extends BaseService<Token> {

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public TokenService(VicRepository<Token> repository) {
        super(repository);
    }

    public LoginRespostaDto logaGoogle(String token) {
        LoginRespostaDto r = new LoginRespostaDto();
        Map verify = GoogleToken.verify(token);
        String email = (String) verify.get("email");
        if (email == null) {
            r.mensagem = "Erro em Usuário ou senha";
            return r;
        }
        VQuery vQuery = new VQuery(new Criteria("login", ComparisonOperator.EQUAL, email.trim()));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        List<Usuario> findByHql = usuarioService.findByHql(query);

        if (findByHql.size() == 0) {
            VicThreadScope.gi.set("GOOGLE");
            VicThreadScope.ui.set("GOOGLE");
            VicThreadScope.oi.set("GOOGLE.");
            VicThreadScope.defaultRights.set(RightsHelper.OWNER_ALL + RightsHelper.GROUP_READ_UPDATE + RightsHelper.OTHER_READ);
            Usuario u = new Usuario();
            BaseEntityHelper.setBaseEntityFields(u);
            u.setLogin((String) verify.get("email"));
            u.setSenha("123"); //TODO MUDAR 

            Grupo g0 = new Grupo();
            BaseEntityHelper.setBaseEntityFields(g0);
            g0.setCodigo(email.replaceAll("\\.", "_"));
            g0.setNome(email);
            grupoRepository.save(g0);

            Organizacao o1 = new Organizacao();
            BaseEntityHelper.setBaseEntityFields(o1);
            String fa = verify.get("family_name").toString();
            o1.setCodigo(fa.replaceAll(" ", "_"));
            o1.setNome(fa);
            organizacaoRepository.save(o1);

            u.setGrupos(new HashSet<>());
            u.getGrupos().add(g0);
            u.setOrganizacao(o1);

            u = usuarioRepository.save(u);
            r.mensagem = "Usuário criado, Login OK";
            r.ok = true;
            r.token = criaToken(u);
        } else if (findByHql.size() == 1) {
            r.mensagem = "Login OK";
            r.ok = true;
            r.token = criaToken(findByHql.get(0));
        } else if (findByHql.size() > 0) {
            r.mensagem = "Multiplos Usuários";
        }
        return r;
    }

    public LoginRespostaDto loga(LoginDto login) {
        LoginRespostaDto r = new LoginRespostaDto();

        VQuery vQuery = new VQuery(LogicalOperator.AND, new Criteria(), Arrays.asList(new VQuery[]{
            new VQuery(new Criteria("login", ComparisonOperator.EQUAL, login.login.trim())),
            new VQuery(new Criteria("senha", ComparisonOperator.EQUAL, login.senha.trim()))
        }));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        List<Usuario> findByHql = usuarioService.findByHql(query);
        if (findByHql.size() == 0) {
            r.mensagem = "Erro em Usuário ou senha";
        } else if (findByHql.size() == 1) {
            r.mensagem = "Login OK";
            r.ok = true;
            r.token = criaToken(findByHql.get(0));
        } else if (findByHql.size() > 0) {
            r.mensagem = "Multiplos Usuários";
        }
        return r;
    }

    private Token criaToken(Usuario usuario) {
        Token t = newEntity();
        t.setToken(t.getId());
        t.setUsuario(usuario);
        return repository.save(t);
    }

    public LoginRespostaDto logout() {
        //Token tok = repository.findOne("aaa");
        LoginRespostaDto lr = new LoginRespostaDto();
        lr.codigo = 0;
        lr.mensagem = "Volte sempre";
        lr.ok = true;
        lr.token = null;
        return lr;
    }

    public Token findUserByToken(String tokenValue) {
        Token token = repository.findOne(tokenValue);
        return token;
    }

}
