/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.VicThreadScope;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.Group;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.dto.PrivilegesAssignmentDto;
import br.com.munif.framework.vicente.security.repository.UserRepository;
import br.com.munif.framework.vicente.security.service.interfaces.IUserService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GeradorVicente
 */
@Service
public class UserService extends BaseService<User> implements IUserService {

    private GroupService groupService;

    public UserService(VicRepository<User> repository, GroupService groupService) {
        super(repository);
        this.groupService = groupService;
    }

    public UserRepository getRepository() {
        return (UserRepository) repository;
    }

    @Transactional
    public void assignPrivileges(PrivilegesAssignmentDto privilegesAssignmentDto) {
        List<Group> groups = groupService.getGroupsByCode(privilegesAssignmentDto.groupCode);
        User user = getUserByLogin(privilegesAssignmentDto.login);
        user.assignGroups(groups);
        save(user);
    }

    @Transactional
    public User getCurrentUser() {
        return getRepository().getUserByLogin(VicThreadScope.ui.get());
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return findByHqlNoTenancy(new VicQuery(new VQuery(new Criteria("login", ComparisonOperator.EQUAL, login)))).stream().findFirst().orElse(null);
    }


    @Transactional(readOnly = true)
    public List<User> findUsersByEmail(String email) {
        VQuery vQuery = new VQuery(new Criteria("login", ComparisonOperator.EQUAL, email.trim()));
        VicQuery query = new VicQuery();
        query.setQuery(vQuery);
        return findByHqlNoTenancy(query);
    }

    @Override
    @Transactional
    public User loadNoTenancy(String id) {
        User user = super.loadNoTenancy(id);
        if (user != null) {
            Hibernate.initialize(user.getGroups());
            Hibernate.initialize(user.getOrganizations());
        }
        return user;
    }

    @Override
    @Transactional
    public User save(User resource) {
        User user = loadNoTenancy(resource.getId());
        if (user != null && resource.getPassword() == null) {
            resource.setPassword(user.getPassword());
        }
        return super.save(resource);
    }
}
