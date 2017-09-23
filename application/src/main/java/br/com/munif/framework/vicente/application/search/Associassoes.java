package br.com.munif.framework.vicente.application.search;

/**
 *
 * @author munif
 */
public class Associassoes {

    private String atributo;
    private String entidadeOrigem;
    private String entidadeDestino;
    private String multiplicidade;

    public Associassoes() {
    }

    public Associassoes(String atributo, String entidadeOrigem, String entidadeDestino, String multiplicidade) {
        this.atributo = atributo;
        this.entidadeOrigem = entidadeOrigem;
        this.entidadeDestino = entidadeDestino;
        this.multiplicidade = multiplicidade;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getEntidadeOrigem() {
        return entidadeOrigem;
    }

    public void setEntidadeOrigem(String entidadeOrigem) {
        this.entidadeOrigem = entidadeOrigem;
    }

    public String getEntidadeDestino() {
        return entidadeDestino;
    }

    public void setEntidadeDestino(String entidadeDestino) {
        this.entidadeDestino = entidadeDestino;
    }

    public String getMultiplicidade() {
        return multiplicidade;
    }

    public void setMultiplicidade(String multiplicidade) {
        this.multiplicidade = multiplicidade;
    }

    @Override
    public String toString() {
        return "Associassoes{" + "atributo=" + atributo + ", entidadeOrigem=" + entidadeOrigem + ", entidadeDestino=" + entidadeDestino + ", multiplicidade=" + multiplicidade + '}';
    }

}
