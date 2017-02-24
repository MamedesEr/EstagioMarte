package javafxmvc.model.domain;

/**
 *
 * @author Mattheus
 */
public class Chave {
    private int idChave;
    private String identificador;
    private String descricao;
    
    public Chave(){
        
    }
    
    public Chave(int idChave, String identificador, String descricao) {
        this.idChave = idChave;
        this.identificador = identificador;
        this.descricao = descricao;
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
    
}
