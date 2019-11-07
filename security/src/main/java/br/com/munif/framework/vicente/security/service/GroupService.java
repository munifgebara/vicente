/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.security.domain.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GeradorVicente
 */
@Service
public class GroupService extends BaseService<Group> {
    public GroupService(VicRepository<Group> repository) {
        super(repository);
    }

    public Group createGroupByEmail(String email) {
        Group g0 = new Group();
        g0.setCode(email.replaceAll("\\.", "_"));
        g0.setName("Group " + email);
        return repository.save(g0);
    }
}
