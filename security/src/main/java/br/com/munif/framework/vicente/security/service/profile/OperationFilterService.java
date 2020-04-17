/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service.profile;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;
import br.com.munif.framework.vicente.security.domain.profile.RequestAction;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GeradorVicente
 */
@Service
public class OperationFilterService extends BaseService<OperationFilter> {
    private final RequestActionService requestActionService;

    public OperationFilterService(VicRepository<OperationFilter> repository, RequestActionService requestActionService) {
        super(repository);
        this.requestActionService = requestActionService;
    }

    @Transactional(readOnly = true)
    public OperationFilter findByOperationKeyAndLogin(String api, String method, String login) {
        List<OperationFilter> byHqlNoTenancy = findByHqlNoTenancy(new VicQuery(
                new VQuery(new Criteria("profile.user.login", ComparisonOperator.EQUAL, login))
                        .and(new Criteria("operation.api", ComparisonOperator.EQUAL, api))
                        .and(new Criteria("operation.method", ComparisonOperator.EQUAL, method))
        ));
        if (byHqlNoTenancy.size() > 0) {
            OperationFilter operationFilter = byHqlNoTenancy.get(byHqlNoTenancy.size() - 1);
            Hibernate.initialize(operationFilter.getActions());
            return operationFilter;
        }
        return new OperationFilter(new ArrayList<>());
    }

    @Override
    @Transactional
    public OperationFilter save(OperationFilter resource) {
        if (resource.getActions() != null) {
            for (RequestAction forwardRequest : resource.getActions()) {
                forwardRequest.setOperationFilter(resource);
                forwardRequest = requestActionService.save(forwardRequest);
            }
        }
        return super.save(resource);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationFilter loadNoTenancy(String id) {
        OperationFilter operationFilter = super.loadNoTenancy(id);
        Hibernate.initialize(operationFilter.getActions());
        return operationFilter;
    }
}
