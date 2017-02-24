package javafxmvc.model.domain;

/**
 *
 * @author Mattheus
 */
public class Usuario {
    
    private int idUsuario;
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    
    public Usuario(){
        
    }

    public Usuario(int idUsuario, String login, String senha, String nome, String cpf, String telefone, String email) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

}
