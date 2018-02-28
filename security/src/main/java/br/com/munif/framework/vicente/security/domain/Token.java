package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.core.VicTenancyPolicy;
import br.com.munif.framework.vicente.core.VicTenancyType;
import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import java.time.ZonedDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;

@Entity
@Audited
@VicTenancyPolicy(VicTenancyType.COMMUM)
public class Token extends BaseEntity {

    private String token;

    @ManyToOne
    private Usuario usuario;
    private Long expiracao;

    public Token() {
        expiracao = System.currentTimeMillis() + 5 * 60 * 1000;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(Long expiracao) {
        this.expiracao = expiracao;
    }

}
