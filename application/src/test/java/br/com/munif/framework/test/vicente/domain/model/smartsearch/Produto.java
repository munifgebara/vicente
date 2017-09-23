package br.com.munif.framework.test.vicente.domain.model.smartsearch;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
public class Produto extends BaseEntity {
    private String nome;

    private Integer quantidade;

    private BigDecimal valor;

    @ManyToOne
    private Categoria categoria;
    
    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itens;

    public Produto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto{" + "nome=" + nome + ", quantidade=" + quantidade + ", valor=" + valor + '}';
    }
    
    
    
}
