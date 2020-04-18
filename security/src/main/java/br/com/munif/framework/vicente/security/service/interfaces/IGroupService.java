package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.Group;

public interface IGroupService extends VicServiceable<Group> {
    Group createGroupByEmail(String email);
}
