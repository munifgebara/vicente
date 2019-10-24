package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.api.BaseAPI;
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
@RequestMapping("/api/pessoagenerica")
public class PessoaGenericaApi extends BaseAPI<PessoaGenerica> {

    private final Logger log = LoggerFactory.getLogger(PessoaGenericaApi.class);

    private static final String ENTITY_NAME = "PessoaGenerica";

    public PessoaGenericaApi(BaseService<PessoaGenerica> service) {
        super(service);
    }

}
