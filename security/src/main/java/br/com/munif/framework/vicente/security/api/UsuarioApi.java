/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.security.domain.Usuario;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioApi extends BaseAPI<Usuario> {

    private final Logger log = LogManager.getLogger(UsuarioApi.class);

    private static final String ENTITY_NAME = "usuario";

    public UsuarioApi(BaseService<Usuario> service) {
        super(service);
    }
    

}
