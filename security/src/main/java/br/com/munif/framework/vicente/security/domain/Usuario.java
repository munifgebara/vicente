package br.com.munif.framework.vicente.security.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import java.time.ZonedDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Usuario extends BaseEntity {

    @Column(unique = true)
    private String login;

    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Grupo> grupos;

    @ManyToOne
    private Organizacao organizacao;

    public Usuario() {
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Organizacao getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(Organizacao organizacao) {
        this.organizacao = organizacao;
    }

    public String stringGrupos() {
        if (this.getGrupos() == null) {
            return null;
        }
        String s = "";
        for (Grupo g : this.getGrupos()) {
            s += g.getCodigo() + ",";
        }

        return s;

    }

    public String getStringOrganizacao() {
        if (organizacao == null) {
            return null;
        }
        return getOrganizacao().getId();
    }

}
