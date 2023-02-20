package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.domain.dto.ChangePasswordDto;
import br.com.munif.framework.vicente.security.domain.dto.PrivilegesAssignmentDto;
import br.com.munif.framework.vicente.security.domain.dto.ValidateEmailDto;
import br.com.munif.framework.vicente.security.domain.dto.ValidateEmailStatus;

import java.util.List;

public interface IUserService extends VicServiceable<User> {
    List<User> findUsersByEmail(String email);

    void assignPrivileges(PrivilegesAssignmentDto privileges);

    User updateImage(String id, String imageUrl);
    ValidateEmailStatus validate(ValidateEmailDto validateEmailDto);

    void changePassword(String id, ChangePasswordDto changePasswordDto);
}
