/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api.profile;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.security.domain.profile.RequestAction;
import br.com.munif.framework.vicente.security.service.interfaces.IRequestActionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/forward-request")
public class ForwardRequestApi extends BaseAPI<RequestAction> {

    private final Logger log = Logger.getLogger(ForwardRequestApi.class.getSimpleName());

    public ForwardRequestApi(IRequestActionService service) {
        super(service);
    }
}
