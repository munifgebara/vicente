/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.api.errors;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.domain.tenancyfields.VicField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author munif
 */
@RestController
@RequestMapping("/api/vicfield")
public class VicFieldsApi extends BaseAPI<VicField> {

    private static final String ENTITY_NAME = "FIELDS";
    private final Logger log = LogManager.getLogger(VicFieldsApi.class);

    public VicFieldsApi(BaseService<VicField> service) {
        super(service);
    }

}
