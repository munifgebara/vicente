/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 02:07:54 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.core.VicPublicOperation;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.dto.LoginDto;
import br.com.munif.framework.vicente.security.domain.dto.LoginResponseAppDto;
import br.com.munif.framework.vicente.security.domain.dto.LoginResponseDto;
import br.com.munif.framework.vicente.security.domain.dto.RefreshTokenDto;
import br.com.munif.framework.vicente.security.service.interfaces.ITokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/token")
public class TokenApi extends BaseAPI<Token> {

    private static final String ENTITY_NAME = "token";
    private final Logger log = LogManager.getLogger(TokenApi.class);
    private final ITokenService tokenService;

    public TokenApi(ITokenService service) {
        super(service);
        this.tokenService = (ITokenService) service;
    }

    @Transactional
    @VicPublicOperation
    @RequestMapping(value = "/login/bypassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto loga(@RequestBody LoginDto login) {
        return tokenService.login(login);
    }
    @Transactional
    @VicPublicOperation
    @RequestMapping(value = "/login/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseAppDto integrationToken(@RequestBody LoginDto login) {
        return tokenService.appLogin(login);
    }
    @Transactional
    @VicPublicOperation
    @RequestMapping(value = "/login/bytoken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto loginByToken() {
        return tokenService.loginByToken();
    }

    @Transactional
    @VicPublicOperation
    @RequestMapping(value = "/refresh-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public RefreshTokenDto loga(@RequestBody RefreshTokenDto refreshTokenDto) {
        return tokenService.refreshToken(refreshTokenDto);
    }

    @Transactional
    @VicPublicOperation
    @RequestMapping(value = "/login/bygoogle", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto logaGoogle(@RequestBody String token) {
        return tokenService.loginOnGoogle(token);
    }

    @Transactional
    @RequestMapping(value = "/sigin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto sigin(@RequestBody LoginDto login) {
        return tokenService.sigin(login);
    }

    @Transactional
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto logout() {
        return tokenService.logout();
    }

    @Transactional
    @RequestMapping(value = "/login/bypassword/{login}/{senha:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto logaGet(@PathVariable String login, @PathVariable String senha) {
        return loga(new LoginDto(login, senha));
    }

    @Transactional
    @VicPublicOperation
    @GetMapping(value = "/recover-password/{id:.+}")
    public ResponseEntity<Void> recoverPassword(@PathVariable("id") String id) {
        tokenService.recoverPassword(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @VicPublicOperation
    @GetMapping(value = "/search-ticket/{id:.+}")
    public ResponseEntity<Map> searchTicket(@PathVariable("id") String id) {
        return ResponseEntity.ok(tokenService.searchTicket(id));
    }

    @Transactional
    @GetMapping(value = "/lost-password/{ticket:.+}/{password:.+}")
    public ResponseEntity<Map> lostPassword(@PathVariable("ticket") String ticket, @PathVariable("password") String password) {
        return ResponseEntity.ok(tokenService.lostPassword(ticket, password));
    }

    @Transactional
    @GetMapping(value = "/change-organization/{organizationId:.+}")
    public ResponseEntity<Map> changeOrganization(@PathVariable("organizationId") String organizationId) {
        return ResponseEntity.ok(tokenService.changeOrganization(organizationId));
    }
}
