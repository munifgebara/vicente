package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseResource;
import br.com.munif.framework.vicente.api.test.apptest.domain.Salario;
import br.com.munif.framework.vicente.application.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author munif
 */
@RestController
@RequestMapping("/api/salario-resource")
public class SalarioResource extends BaseResource<Salario> {

    private static final String ENTITY_NAME = "salario";
    private final Logger log = LoggerFactory.getLogger(SalarioResource.class);

    public SalarioResource(BaseService<Salario> service) {
        super(service);
    }

}
