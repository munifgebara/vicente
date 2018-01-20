package br.com.munif.framework.test.vicente.application;


import br.com.munif.framework.test.vicente.domain.model.Consultor;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author munif
 */
@Service
public class ConsultorService extends BaseService<Consultor>{
    
    public ConsultorService(VicRepository<Consultor> repository) {
        super(repository);
    }
    
    
    
}
