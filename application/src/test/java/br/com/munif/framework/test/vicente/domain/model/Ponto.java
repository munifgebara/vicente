package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.domain.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Audited
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
