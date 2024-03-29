/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.security.domain.Organization;
import br.com.munif.framework.vicente.security.service.interfaces.IOrganizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/organization")
public class OrganizationApi extends BaseAPI<Organization> {

    private static final String ENTITY_NAME = "organization";
    private final Logger log = LogManager.getLogger(OrganizationApi.class);

    public OrganizationApi(IOrganizationService service) {
        super(service);
    }


}
