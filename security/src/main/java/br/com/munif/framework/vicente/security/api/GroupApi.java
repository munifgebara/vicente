/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.security.domain.Group;
import br.com.munif.framework.vicente.security.service.interfaces.IGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/group")
public class GroupApi extends BaseAPI<Group> {

    private static final String ENTITY_NAME = "group";
    private final Logger log = Logger.getLogger(GroupApi.class.getSimpleName());

    public GroupApi(IGroupService service) {
        super(service);
    }
}
