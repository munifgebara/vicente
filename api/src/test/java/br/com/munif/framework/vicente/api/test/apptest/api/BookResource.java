package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseResource;
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
@RequestMapping("/api/book-resource")
public class BookResource extends BaseResource<Book> {

    private static final String ENTITY_NAME = "books";
    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    public BookResource(BaseService<Book> service) {
        super(service);
    }

}
