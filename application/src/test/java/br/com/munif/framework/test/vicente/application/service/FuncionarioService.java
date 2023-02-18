package br.com.munif.framework.test.vicente.application.service;


import br.com.munif.framework.test.vicente.domain.model.Funcionario;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 * @author munif
 */
@Service
public class FuncionarioService extends BaseService<Funcionario> {
    public FuncionarioService(VicRepository<Funcionario> repository) {
        super(repository);
    }
}
