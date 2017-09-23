package br.com.munif.framework.test.vicente.domain.model.smartsearch;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.math.BigDecimal;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author munif
 */
@Entity
@Audited
public class ItemPedido extends BaseEntity {

    private Integer quantidade;

    private BigDecimal valorUnitario;

    @ManyToOne
    private Produto produto;
    
    @ManyToOne
    private Pedido pedido;

    public ItemPedido() {
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
