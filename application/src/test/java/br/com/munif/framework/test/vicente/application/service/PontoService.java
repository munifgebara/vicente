package br.com.munif.framework.test.vicente.application.service;

import br.com.munif.framework.test.vicente.domain.model.Ponto;
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
