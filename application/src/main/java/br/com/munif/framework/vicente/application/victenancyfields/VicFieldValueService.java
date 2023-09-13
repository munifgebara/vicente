package br.com.munif.framework.vicente.application.victenancyfields;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import br.com.munif.framework.vicente.domain.tenancyfields.VicFieldValue;
import org.springframework.stereotype.Service;

/**
 * @author munif
 */
@Service
public class VicFieldValueService extends BaseService<VicFieldValue> {
    public VicFieldValueService(VicRepository<VicFieldValue> repository) {
        super(repository);
    }
}
