/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.security.domain.Group;
import org.springframework.stereotype.Service;

/**
 * @author GeradorVicente
 */
@Service
public class GrupoService extends BaseService<Group> {
    public GrupoService(VicRepository<Group> repository) {
        super(repository);
    }
}
