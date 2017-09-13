package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author munif
 */
public class VicToken extends BaseEntity{
    
    private String value;
    
    private VicUser user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VicUser getUser() {
        return user;
    }

    public void setUser(VicUser user) {
        this.user = user;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
    
    
   
    
    
}
