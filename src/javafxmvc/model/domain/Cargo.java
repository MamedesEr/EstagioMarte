package javafxmvc.model.domain;

/**
 *
 * @author Mattheus
 */
public class Cargo {
    
    private int idCargo;
    private String descricao;

    public Cargo() {
    }
    
    public Cargo(int idCargo, String descricao) {
        this.idCargo = idCargo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
    
}
