package javafxmvc.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
        
public class FXMLFrmCadastroController{
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button btnPessoa;

    @FXML
    private Button btnChave;

    @FXML
    private Button btnDepartamento;

    @FXML
    private Button btnCargo;

    @FXML
    private Button btnUsuario;

    @FXML
    void btnUsuario_onAction() throws IOException {
        //Abrindo a tela de cadastro do usuario
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastroUsuario.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);
    }

    @FXML
    void btnChave_onAction() throws IOException {
        //Abrindo a tela de cadastro de chave
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastroChave.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);
    }

    @FXML
    void btnDepartamento_onAction() throws IOException {
        //Abrindo a tela de cadastro do departamento
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastroDepartamento.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);
    }

    @FXML
    void btnCargo_onAction() throws IOException {
        //Abrindo a tela de cadastro do cargo
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastroCargo.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);
    }

    @FXML
    void btnPessoa_onAction() throws IOException {
        //Abrindo a tela de cadastro da pessoa
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastroPessoa.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);
    }
}
