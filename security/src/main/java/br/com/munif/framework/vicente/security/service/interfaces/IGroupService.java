package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.Group;

import java.util.List;

public interface IGroupService extends VicServiceable<Group> {
    Group createGroupByEmail(String email);

    List<Group> getGroupsByCode(String groupCode);
}
