package javafxmvc.model.domain;

/**
 *
 * @author Mattheus
 */
public class Pessoa {
    private int idPessoa;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    
    public Pessoa(){
        
    }
    
    public Pessoa(int idPessoa, String nome, String cpf, String telefone, String email) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
