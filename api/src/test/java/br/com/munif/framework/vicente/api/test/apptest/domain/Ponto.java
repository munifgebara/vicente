package br.com.munif.framework.vicente.api.test.apptest.domain;

import br.com.munif.framework.vicente.domain.BaseEntity;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "ponto2")
public class Ponto extends BaseEntity {

    private String nome;
    private ZonedDateTime entrada;

    private ZonedDateTime saida;

    public Ponto() {
        nome = "exemplo";
        entrada = ZonedDateTime.now();
        saida = entrada.plus(4, ChronoUnit.HOURS);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ZonedDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(ZonedDateTime entrada) {
        this.entrada = entrada;
    }

    public ZonedDateTime getSaida() {
        return saida;
    }

    public void setSaida(ZonedDateTime saida) {
        this.saida = saida;
    }

}
