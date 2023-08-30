package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.Organization;

public interface IOrganizationService extends VicServiceable<Organization> {
    Organization createOrganizationByEmail(String email);
}
