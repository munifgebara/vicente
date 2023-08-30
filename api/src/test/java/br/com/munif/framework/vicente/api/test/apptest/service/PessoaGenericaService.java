package br.com.munif.framework.vicente.api.test.apptest.service;


import br.com.munif.framework.vicente.api.test.apptest.domain.PessoaGenerica;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 * @author munif
 */
@Service
public class PessoaGenericaService extends BaseService<PessoaGenerica> {

    public PessoaGenericaService(VicRepository<PessoaGenerica> repository) {
        super(repository);
    }


}
