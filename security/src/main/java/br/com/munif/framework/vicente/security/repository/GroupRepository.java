/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.repository;

import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.security.domain.Group;
import org.springframework.stereotype.Repository;

/**
 * @author GeradorVicente
 */
@SuppressWarnings("unused")
@Repository
public interface GroupRepository extends VicRepository<Group> {
    Group getGroupByCode(String code);
}
