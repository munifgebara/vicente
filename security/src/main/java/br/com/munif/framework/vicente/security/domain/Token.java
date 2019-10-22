package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author munif
 */
@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.COMMUM)
public class Token extends BaseEntity {

    @Column(name = "value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expiration")
    private Long expiration;

    public Token() {
        expiration = System.currentTimeMillis() + 5 * 60 * 1000;
    }

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

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

}
