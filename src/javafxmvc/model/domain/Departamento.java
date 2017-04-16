package javafxmvc.model.domain;

/**
 *
 * @author Mattheus
 */
public class Departamento {
    private int idDepartamento;
    private String nome;
    private String sigla;
    private String chefia;
    private String telefone;
    private String email;
    private String observacao;

    public Departamento() {
    }

    public Departamento(int idDepartamento, String nome, String sigla, String chefia, String telefone, String email, String observacao) {
        this.idDepartamento = idDepartamento;
        this.nome = nome;
        this.sigla = sigla;
        this.chefia = chefia;
        this.telefone = telefone;
        this.email = email;
        this.observacao = observacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getChefia() {
        return chefia;
    }

    public void setChefia(String chefia) {
        this.chefia = chefia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
