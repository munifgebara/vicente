package br.com.munif.framework.test.vicente.application.service;


import br.com.munif.framework.test.vicente.domain.model.Pessoa;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author munif
 */
@Service
public class PessoaService extends BaseService<Pessoa>{
    
    public PessoaService(VicRepository<Pessoa> repository) {
        super(repository);
    }
    
    
    
}
