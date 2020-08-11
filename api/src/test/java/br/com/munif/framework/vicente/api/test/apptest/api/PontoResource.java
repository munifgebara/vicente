package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseResource;
import br.com.munif.framework.vicente.api.test.apptest.domain.Ponto;
import br.com.munif.framework.vicente.application.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author munif
 */
@RestController
@RequestMapping("/api/ponto-resource")
public class PontoResource extends BaseResource<Ponto> {

    private final Logger log = LoggerFactory.getLogger(PontoResource.class);

    private static final String ENTITY_NAME = "contato";

    public PontoResource(BaseService<Ponto> service) {
        super(service);
    }

}
