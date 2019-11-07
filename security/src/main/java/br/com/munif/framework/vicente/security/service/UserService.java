/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.Group;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.dto.PrivilegesAssignmentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GeradorVicente
 */
@Service
public class UserService extends BaseService<User> {

    private GroupService groupService;

    public UserService(VicRepository<User> repository, GroupService groupService) {
        super(repository);
        this.groupService = groupService;
    }

    @Transactional
    public User assignPrivileges(PrivilegesAssignmentDto privilegesAssignmentDto) {
        List<Group> groups = groupService.getGroupsByCode(privilegesAssignmentDto.groupCode);
        User user = getUserByLogin(privilegesAssignmentDto.login);
        user.assignGroups(groups);
        return save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return findByHqlNoTenancy(new VicQuery(new VQuery(new Criteria("login", ComparisonOperator.EQUAL, login)))).stream().findFirst().orElse(null);
    }
}
