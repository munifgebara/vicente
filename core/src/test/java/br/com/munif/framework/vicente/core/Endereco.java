package br.com.munif.framework.vicente.core;

public class Endereco {
    private String descricao;
    private Long numero;
    private Boolean principal;

    private Coordenadas coordenadas;

    public Endereco(String descricao, Long numero, Boolean principal, Coordenadas coordenadas) {
        this.descricao = descricao;
        this.numero = numero;
        this.principal = principal;
        this.coordenadas = coordenadas;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
