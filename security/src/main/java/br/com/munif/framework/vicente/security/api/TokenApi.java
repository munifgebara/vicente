/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 02:07:54 */
 /* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicReturn;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.dto.LoginRespostaDto;
import br.com.munif.framework.vicente.security.service.TokenService;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/token")
public class TokenApi extends BaseAPI<Token> {

    private final Logger log = LogManager.getLogger(TokenApi.class);

    private static final String ENTITY_NAME = "token";

    @Autowired
    private TokenService tokenService;

    public TokenApi(BaseService<Token> service) {
        super(service);
        this.tokenService = (TokenService) service;
    }

    @Transactional
    @RequestMapping(value = "/login/bypassword", method = RequestMethod.POST)
    public LoginRespostaDto loga(@RequestBody LoginDto login) {
        LoginRespostaDto r = tokenService.loga(login);
        return r;
    }

    @Transactional
    @RequestMapping(value = "/login/bygoogle", method = RequestMethod.POST)
    public LoginRespostaDto logaGoogle(@RequestBody String token) {
        LoginRespostaDto r = tokenService.logaGoogle(token);
        return r;
    }


    @Transactional
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public LoginRespostaDto logout() {
        return tokenService.logout();
        
    }

    @Transactional
    @RequestMapping(value = "/login/bypassword/{login}/{senha:.+}", method = RequestMethod.GET)
    public LoginRespostaDto logaGet(@PathVariable String login, @PathVariable String senha) {
        return loga(new LoginDto(login, senha));
    }

}
