/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.security.domain.Organization;
import br.com.munif.framework.vicente.security.service.interfaces.IOrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GeradorVicente
 */
@Service
public class OrganizationService extends BaseService<Organization> implements IOrganizationService {
    public OrganizationService(VicRepository<Organization> repository) {
        super(repository);
    }

    @Transactional
    public Organization createOrganizationByEmail(String email) {
        Organization o1 = new Organization();
        o1.setCode(email.replaceAll(" ", "_") + ".");
        o1.setName("Organization " + email);
        return repository.save(o1);
    }
}
