package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author munif
 */
public class Token extends BaseEntity{
    
    private String value;
    
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
    
    
   
    
    
}
