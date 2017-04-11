package javafxmvc.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLFrmCadastroUsuarioController {
    @FXML
    private Tab tabConsulta;

    @FXML
    private AnchorPane anchorPaneConsulta;

    @FXML
    private AnchorPane anchorPaneBotoes;

    @FXML
    private Label lbUsuario;

    @FXML
    private ComboBox<?> comboBoxDepartamento;

    @FXML
    private Button btnInserir;

    @FXML
    private Tab tabAlterar;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private AnchorPane anchorPaneAlterar;

    @FXML
    private Label lbDigite;

    @FXML
    private TextField txtLogin;

    @FXML
    private Button btnSalvar;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnVoltar;

    @FXML
    private AnchorPane anchorPaneRodape;

    @FXML
    private Label lbDepartamento;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Label lbCPF;

    @FXML
    private Label lbEmail;

    @FXML
    private TableColumn<?, ?> tableColumnLogin;

    @FXML
    private Label lbCargo;

    @FXML
    private TextField txtSenha;

    @FXML
    private Label lbSenha;

    @FXML
    private TableColumn<?, ?> tableColumnNome;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnPesquisa;

    @FXML
    private TableColumn<?, ?> tableColumnCodigo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Label lbLogin;

    @FXML
    private Label lbCodigo;

    @FXML
    private Label lbTelefone;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Button btnAlterar;

    @FXML
    private Label lbNome;

    @FXML
    private TabPane tabPane;

    @FXML
    private ComboBox<?> comboBoxCargo;
    
    @FXML
    void btnVoltar_onAction() throws IOException {
        //Abrindo a tela de cadastro
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastro.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);    
    }
    
    @FXML
    void btnSalvar_onAction(ActionEvent event) {

    }

    @FXML
    void btnCancelar_onAction(ActionEvent event) {

    }
}
