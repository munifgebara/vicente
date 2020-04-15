/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.service.profile;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.core.VicQuery;
import br.com.munif.framework.vicente.core.vquery.ComparisonOperator;
import br.com.munif.framework.vicente.core.vquery.Criteria;
import br.com.munif.framework.vicente.core.vquery.VQuery;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.profile.ForwardRequest;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;
import com.google.common.collect.Iterables;
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
    private final ForwardRequestService forwardRequestService;

    public OperationFilterService(VicRepository<OperationFilter> repository, ForwardRequestService forwardRequestService) {
        super(repository);
        this.forwardRequestService = forwardRequestService;
    }

    @Transactional(readOnly = true)
    public OperationFilter findByOperationKeyAndToken(String api, String method, Token token) {
        if (token == null || token.getUser() == null) return new OperationFilter(new ArrayList<>());
        List<OperationFilter> byHqlNoTenancy = findByHqlNoTenancy(new VicQuery(
                new VQuery(new Criteria("user.id", ComparisonOperator.EQUAL, token.getUser().getId()))
                        .and(new Criteria("operation.api", ComparisonOperator.EQUAL, api))
                        .and(new Criteria("operation.method", ComparisonOperator.EQUAL, method))
        ));
        OperationFilter last = Iterables.getLast(byHqlNoTenancy, new OperationFilter(new ArrayList<>()));
        return last;
    }

    @Override
    @Transactional
    public OperationFilter save(OperationFilter resource) {
        if (resource.getForwardRequests() != null) {
            for (ForwardRequest forwardRequest : resource.getForwardRequests()) {
                forwardRequest.setOperationFilter(resource);
                forwardRequest = forwardRequestService.save(forwardRequest);
            }
        }
        return super.save(resource);
    }
}
