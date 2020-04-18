package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.dto.PrivilegesAssignmentDto;

import java.util.List;

public interface IUserService extends VicServiceable<User> {
    List<User> findUsersByEmail(String email);
    void assignPrivileges(PrivilegesAssignmentDto privileges);
}
