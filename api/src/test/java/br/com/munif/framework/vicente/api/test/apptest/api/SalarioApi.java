package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.api.test.apptest.domain.Salario;
import br.com.munif.framework.vicente.application.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author munif
 */
@RestController
@RequestMapping("/api/salario")
public class SalarioApi extends BaseAPI<Salario> {

    private final Logger log = LoggerFactory.getLogger(SalarioApi.class);

    private static final String ENTITY_NAME = "salario";

    public SalarioApi(BaseService<Salario> service) {
        super(service);
    }

}
