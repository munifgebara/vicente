/* Arquivo gerado utilizando VICGERADOR por munif as 28/02/2018 01:55:19 */
/* Para não gerar o arquivo novamente coloque na primeira linha um comentário com  VICIGNORE , pode ser essa mesmo */
package br.com.munif.framework.vicente.security.api;

import br.com.munif.framework.vicente.api.BaseAPI;
import br.com.munif.framework.vicente.application.BaseService;
import br.com.munif.framework.vicente.security.domain.PasswordGenerator;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.dto.PrivilegesAssignmentDto;
import br.com.munif.framework.vicente.security.service.GroupService;
import br.com.munif.framework.vicente.security.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author GeradorVicente
 */
@RestController
@RequestMapping("/api/user")
public class UserApi extends BaseAPI<User> {

    private final Logger log = LogManager.getLogger(UserApi.class);
    private static final String ENTITY_NAME = "user";

    public UserApi(BaseService<User> service) {
        super(service);
    }

    @Transactional
    @RequestMapping(value = "/assign-privileges", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> assignPrivileges(@RequestBody PrivilegesAssignmentDto privileges) {
        ((UserService) service).assignPrivileges(privileges);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> save(@RequestBody @Valid User model) {
        model.setPassword(PasswordGenerator.generate(model.getPassword()));
        return super.save(model);
    }

    @Transactional
    @Override
    @PutMapping(value = "/{id}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> updateWithId(@PathVariable("id") String id, @RequestBody @Valid User model) {
        model.setPassword(PasswordGenerator.generate(model.getPassword()));
        return super.updateWithId(id, model);
    }


}
