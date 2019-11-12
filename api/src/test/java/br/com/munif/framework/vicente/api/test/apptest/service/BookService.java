package br.com.munif.framework.vicente.api.test.apptest.service;


import br.com.munif.framework.vicente.api.test.apptest.domain.Book;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.application.VicRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author munif
 */
@Service
public class BookService extends BaseService<Book>{
    
    public BookService(VicRepository<Book> repository) {
        super(repository);
    }
    
    
    
}
