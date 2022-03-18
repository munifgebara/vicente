package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseResource;
import br.com.munif.framework.vicente.api.test.apptest.domain.PessoaGenerica;
import br.com.munif.framework.vicente.application.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author munif
 */
@RestController
@RequestMapping("/api/pessoa-resource")
public class PessoaResource extends BaseResource<PessoaGenerica> {

    private static final String ENTITY_NAME = "PessoaGenerica";
    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    public PessoaResource(BaseService<PessoaGenerica> service) {
        super(service);
    }

}
