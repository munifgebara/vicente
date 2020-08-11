package br.com.munif.framework.vicente.api.test.apptest.api;

import br.com.munif.framework.vicente.api.BaseResource;
import br.com.munif.framework.vicente.api.test.apptest.domain.Information;
import br.com.munif.framework.vicente.application.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author munif
 */
@RestController
@RequestMapping("/api/information-resource")
public class InformationResource extends BaseResource<Information> {

    private final Logger log = LoggerFactory.getLogger(InformationResource.class);

    private static final String ENTITY_NAME = "information";

    public InformationResource(BaseService<Information> service) {
        super(service);
    }

}
