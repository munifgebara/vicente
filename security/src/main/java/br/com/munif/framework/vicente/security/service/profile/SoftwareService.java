/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service.profile;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.security.domain.profile.Operation;
import br.com.munif.framework.vicente.security.domain.profile.Profile;
import br.com.munif.framework.vicente.security.domain.profile.Software;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GeradorVicente
 */
@Service
public class SoftwareService extends BaseService<Software> {
    public SoftwareService(VicRepository<Software> repository) {
        super(repository);
    }

    @Override
    @Transactional
    public Software save(Software resource) {
        for (Operation operation : resource.getOperations()) {
            operation.setSoftware(resource);
        }
        return super.save(resource);
    }
}
