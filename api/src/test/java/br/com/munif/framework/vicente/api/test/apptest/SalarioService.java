package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author munif
 */
@Service
public class SalarioService extends BaseService<Salario> {

    public SalarioService(VicRepository<Salario> repository) {
        super(repository);
    }

}
