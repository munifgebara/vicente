package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.COMMUM)
@Table(name = "vic_token", indexes = {
        @Index(name = "idx_vic_token_oi", columnList = "oi"),
        @Index(name = "idx_vic_token_ui", columnList = "ui"),
        @Index(name = "idx_vic_token_gi", columnList = "gi"),
        @Index(name = "idx_vic_token_rights", columnList = "rights"),
        @Index(name = "idx_vic_token_value", columnList = "value"),
        @Index(name = "idx_vic_token_user_id", columnList = "user_id"),
})
public class Token extends BaseEntity {

    @Column(name = "value")
    private String value;
    @Column(name = "refresh_token")
    private String refreshToken;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
