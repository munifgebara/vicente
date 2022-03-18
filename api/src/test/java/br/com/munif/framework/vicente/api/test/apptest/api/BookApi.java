package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.api.test.apptest.domain.Book;
import br.com.munif.framework.vicente.application.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author munif
 */
@RestController
@RequestMapping("/api/books")
public class BookApi extends BaseAPI<Book> {

    private static final String ENTITY_NAME = "contato";
    private final Logger log = LoggerFactory.getLogger(BookApi.class);

    public BookApi(BaseService<Book> service) {
        super(service);
    }

}
