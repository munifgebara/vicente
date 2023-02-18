package br.com.munif.framework.test.vicente.domain.model.smartsearch;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author munif
 */
@Entity
@Audited
public class Cliente extends BaseEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "cidade")
    private String cidade;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @ManyToMany()
    private List<GrupoClientes> grupoClientes;

    public Cliente() {
    }

    public Cliente(String nome, String cidade, List<GrupoClientes> grupoClientes) {
        this.nome = nome;
        this.cidade = cidade;
        this.grupoClientes = grupoClientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<GrupoClientes> getGrupoClientes() {
        return grupoClientes;
    }

    public void setGrupoClientes(List<GrupoClientes> grupoClientes) {
        this.grupoClientes = grupoClientes;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nome=" + nome + '}';
    }


}
