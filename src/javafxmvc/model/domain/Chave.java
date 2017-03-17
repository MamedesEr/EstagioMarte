package javafxmvc.model.domain;

/**
 *
 * @author Mattheus
 */
public class Chave {
    private int idChave;
    private String identificador;
    private String descricao;
    private String status;
    
    public Chave(){
        
    }
    
    public Chave(int idChave, String identificador, String descricao, String status) {
        this.idChave = idChave;
        this.identificador = identificador;
        this.descricao = descricao;
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdChave() {
        return idChave;
    }

    public void setIdChave(int idChave) {
        this.idChave = idChave;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
