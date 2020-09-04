package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.profile.OperationFilter;

public interface IOperationFilterService extends VicServiceable<OperationFilter> {
    OperationFilter findByKeyAndLogin(String key, String login);
    void incrementRequestedCount(OperationFilter operationFilter);
}
