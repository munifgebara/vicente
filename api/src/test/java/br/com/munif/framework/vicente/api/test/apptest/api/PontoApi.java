package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.api.test.apptest.domain.Ponto;
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
@RequestMapping("/api/ponto")
public class PontoApi extends BaseAPI<Ponto> {

    private final Logger log = LoggerFactory.getLogger(PontoApi.class);

    private static final String ENTITY_NAME = "contato";

    public PontoApi(BaseService<Ponto> service) {
        super(service);
    }

}
