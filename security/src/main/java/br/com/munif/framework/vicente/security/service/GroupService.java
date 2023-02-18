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
import br.com.munif.framework.vicente.security.repository.GroupRepository;
import br.com.munif.framework.vicente.security.service.interfaces.IGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GeradorVicente
 */
@Service
public class GroupService extends BaseService<Group> implements IGroupService {
    public GroupService(VicRepository<Group> repository) {
        super(repository);
    }

    @Transactional
    public GroupRepository getRepository() {
        return (GroupRepository) repository;
    }

    @Transactional
    public Group getCurrentGroup() {
        return getRepository().getGroupByCode(VicThreadScope.cg.get());
    }

    public Group createGroupByEmail(String email) {
        Group g0 = new Group();
        g0.setCode(email.replaceAll("\\.", "_"));
        g0.setName("Group " + email);
        return repository.save(g0);
    }

    @Transactional(readOnly = true)
    public List<Group> getGroupsByCode(String groupCode) {
        VicQuery query = new VicQuery(new VQuery(new Criteria("code", ComparisonOperator.EQUAL, groupCode)));
        return findByHql(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findByHql(VicQuery query) {
        List<Group> byHql = super.findByHql(query);
        return byHql;
    }
}
