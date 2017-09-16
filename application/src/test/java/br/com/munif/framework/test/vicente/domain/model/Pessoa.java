package br.com.munif.framework.test.vicente.domain.model;

import br.com.munif.framework.vicente.domain.BaseEntity;
import br.com.munif.framework.vicente.domain.typings.VicAddress;
import br.com.munif.framework.vicente.domain.typings.VicEmail;
import br.com.munif.framework.vicente.domain.typings.VicPhone;
import org.hibernate.annotations.Columns;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author munif
 */
@Entity
@Audited
public class Pessoa extends BaseEntity {

    //    @NotNull
    @Column(nullable = false)
    private String nome;
    private String apelido;
    private String documento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date nascimento;
    @Columns(columns = {
            @Column(name = "email_description"),
            @Column(name = "social")
    })
    private VicEmail vicEmail;
    @Columns(columns = {
            @Column(name = "zipCode"),
            @Column(name = "premisseType"),//tipo logradouro
            @Column(name = "premisse"), //logradouro
            @Column(name = "number"),
            @Column(name = "information"),
            @Column(name = "neighbourhood"), //bairro
            @Column(name = "localization"), //localização
            @Column(name = "state"),
            @Column(name = "country"),
            @Column(name = "latitude"),
            @Column(name = "longitude"),
            @Column(name = "formalCode"),
            @Column(name = "statecode")
    })
    private VicAddress endereco;

    @Columns(columns = {
            @Column(name = "phone_description"),
            @Column(name = "type")
    })
    private VicPhone telefone;


    @OneToMany
    private List<Endereco> outrosEnderecos;

    @OneToMany
    private List<Email> outrosEmails;

    @OneToMany
    private List<Telefone> outrosTelefones;

    public Pessoa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public VicEmail getVicEmail() {
        return vicEmail;
    }

    public void setVicEmail(VicEmail vicEmail) {
        this.vicEmail = vicEmail;
    }

    public VicAddress getEndereco() {
        return endereco;
    }

    public void setEndereco(VicAddress endereco) {
        this.endereco = endereco;
    }

    public List<Endereco> getOutrosEnderecos() {
        return outrosEnderecos;
    }

    public void setOutrosEnderecos(List<Endereco> outrosEnderecos) {
        this.outrosEnderecos = outrosEnderecos;
    }

    public List<Email> getOutrosEmails() {
        return outrosEmails;
    }

    public void setOutrosEmails(List<Email> outrosEmails) {
        this.outrosEmails = outrosEmails;
    }

    public VicPhone getTelefone() {
        return telefone;
    }

    public void setTelefone(VicPhone telefone) {
        this.telefone = telefone;
    }

    public List<Telefone> getOutrosTelefones() {
        return outrosTelefones;
    }

    public void setOutrosTelefones(List<Telefone> outrosTelefones) {
        this.outrosTelefones = outrosTelefones;
    }
}
