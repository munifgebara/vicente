/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service.profile;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;
import br.com.munif.framework.vicente.security.domain.profile.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GeradorVicente
 */
@Service
public class ProfileService extends BaseService<Profile> {

    private final OperationFilterService operationFilterService;

    public ProfileService(VicRepository<Profile> repository, OperationFilterService operationFilterService) {
        super(repository);
        this.operationFilterService = operationFilterService;
    }

    @Override
    @Transactional
    public Profile save(Profile resource) {
        for (OperationFilter filter : resource.getFilters()) {
            filter.setProfile(resource);
            filter.setUser(resource.getUser());
            filter = operationFilterService.save(filter);
        }
        return super.save(resource);
    }
}
