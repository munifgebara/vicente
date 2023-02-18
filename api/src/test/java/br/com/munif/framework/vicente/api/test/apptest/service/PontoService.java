package br.com.munif.framework.vicente.api.test.apptest.service;

import br.com.munif.framework.vicente.api.test.apptest.domain.Ponto;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 * @author munif
 */
@Service
public class PontoService extends BaseService<Ponto> {

    public PontoService(VicRepository<Ponto> repository) {
        super(repository);
    }

}
