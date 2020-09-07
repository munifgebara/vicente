/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.Organization;
import br.com.munif.framework.vicente.security.service.interfaces.IOrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public List<Organization> getOrganizationByCode(String code) {
        VicQuery vicQuery = new VicQuery(new VQuery(new Criteria("code", ComparisonOperator.EQUAL, code)));
        return findByHql(vicQuery);
    }
}
