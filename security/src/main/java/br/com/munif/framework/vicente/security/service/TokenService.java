/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 02:07:54 */
 /* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.LogicalOperator;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.Usuario;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.dto.LoginRespostaDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GeradorVicente
 */
@Service
public class TokenService extends BaseService<Token> {

    @Autowired
    private UsuarioService usuarioService;

    public TokenService(VicRepository<Token> repository) {
        super(repository);
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
            r.ok=true;
            r.token=criaToken(findByHql.get(0));
        } else if (findByHql.size() > 0) {
            r.mensagem = "Multiplos Usuários";
        }
        return r;
    }

    private Token criaToken(Usuario usuario) {
        Token t=new Token();
        t.setToken(t.getId());
        t.setUsuario(usuario);
        return repository.save(t);
    }

    public LoginRespostaDto logout() {
        //Token tok = repository.findOne("aaa");
        LoginRespostaDto lr = new LoginRespostaDto();
        lr.codigo=0;
        lr.mensagem="Volte sempre";
        lr.ok=true;
        lr.token=null;
        return lr;
    }

    public Token findUserByToken(String tokenValue) {
        Token token = repository.findOne(tokenValue);
        return token;
    }

}
