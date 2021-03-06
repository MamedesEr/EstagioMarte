package javafxmvc.model.domain;

import javafx.scene.image.ImageView;

public class Chave {
    private int idChave;
    private String identificador;
    private String descricao;
    private String status;
    private int id_emprestimo;
    //
    private ImageView imagem;
    //*
    public Chave(){
        
    }

    public Chave(int idChave, String identificador, String descricao, String status, int id_emprestimo) {
        this.idChave = idChave;
        this.identificador = identificador;
        this.descricao = descricao;
        this.status = status;
        this.id_emprestimo = id_emprestimo;
    }
    //
    public ImageView getImagem() {
        return imagem;
    }
    
    public void setImagem(ImageView imagem) {
        this.imagem = imagem;
    }    
    //*
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

    public int getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(int id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }
    
}
