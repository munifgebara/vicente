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
import br.com.munif.framework.vicente.security.domain.PasswordGenerator;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.domain.dto.ChangePasswordDto;
import br.com.munif.framework.vicente.security.domain.dto.PrivilegesAssignmentDto;
import br.com.munif.framework.vicente.security.domain.exceptions.UserNotFoundException;
import br.com.munif.framework.vicente.security.domain.exceptions.WrongPasswordException;
import br.com.munif.framework.vicente.security.repository.UserRepository;
import br.com.munif.framework.vicente.security.service.interfaces.IGroupService;
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

    private final IGroupService groupService;

    public UserService(VicRepository<User> repository, IGroupService groupService) {
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

    @Override
    @Transactional
    public User updateImage(String id, String imageUrl) {
        User user = loadNoTenancy(id);
        user.setImageUrl(imageUrl);
        return super.save(user);
    }

    @Override
    @Transactional
    public void changePassword(String id, ChangePasswordDto changePasswordDto) {
        User user = findUserByIdOrEmail(id);
        if (user != null) {
            boolean isValid = PasswordGenerator.validate(changePasswordDto.currentPassword, user.getPassword());
            if (isValid) {
                user.setPassword(PasswordGenerator.generate(changePasswordDto.newPassword));
                save(user);
            } else {
                throw new WrongPasswordException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public User findUserByIdOrEmail(String id) {
        VicQuery vicQuery = new VicQuery(new VQuery(new Criteria("id", ComparisonOperator.EQUAL, id))
                .or(new Criteria("login", ComparisonOperator.EQUAL, id)));
        List<User> byHql = findByHqlNoTenancy(vicQuery);
        User user = byHql.size() > 0 ? byHql.get(0) : null;
        return user;
    }
}
