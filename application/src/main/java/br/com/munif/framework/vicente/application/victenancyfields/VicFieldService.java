package br.com.munif.framework.vicente.application.victenancyfields;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import org.springframework.stereotype.Service;

/**
 * @author munif
 */
@Service
public class VicFieldService extends BaseService<VicField> {
    public VicFieldService(VicRepository<VicField> repository) {
        super(repository);
    }
}
