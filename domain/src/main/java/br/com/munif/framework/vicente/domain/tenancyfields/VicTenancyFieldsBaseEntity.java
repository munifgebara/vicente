package br.com.munif.framework.vicente.domain.tenancyfields;

import br.com.munif.framework.vicente.domain.BaseEntity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author munif
 */
@MappedSuperclass
public class VicTenancyFieldsBaseEntity extends BaseEntity {

    @Transient
    private final Map<String, VicFieldValue> customFields;

    public VicTenancyFieldsBaseEntity() {
        super();
        customFields = new HashMap<>();
    }

    public Map<String, VicFieldValue> getCustomFields() {
        return customFields;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + "VicTenancyFields=" + customFields + '}';
    }
}
