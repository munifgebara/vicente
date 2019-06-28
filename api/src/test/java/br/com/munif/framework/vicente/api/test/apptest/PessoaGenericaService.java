package br.com.munif.framework.vicente.api.test.apptest;


import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author munif
 */
@Service
public class PessoaGenericaService extends BaseService<PessoaGenerica>{
    
    public PessoaGenericaService(VicRepository<PessoaGenerica> repository) {
        super(repository);
    }
    
    
    
    
    
}
