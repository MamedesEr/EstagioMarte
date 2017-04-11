package javafxmvc.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLFrmCadastroDepartamentoController {
    @FXML
    private Tab tabConsulta;

    @FXML
    private AnchorPane anchorPaneConsulta;

    @FXML
    private Label lbObservacao;

    @FXML
    private AnchorPane anchorPaneBotoes;

    @FXML
    private Label lbUsuario;

    @FXML
    private Button btnInserir;

    @FXML
    private Tab tabAlterar;

    @FXML
    private TextField txtNome;

    @FXML
    private AnchorPane anchorPaneAlterar;

    @FXML
    private Label lbDigite;

    @FXML
    private TextArea txtAreaObservacao;

    @FXML
    private Button btnSalvar;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnVoltar;

    @FXML
    private AnchorPane anchorPaneRodape;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Label lbEmail;

    @FXML
    private TextField txtSigla;

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
    private Label lbChefia;

    @FXML
    private Label lbCodigo;

    @FXML
    private Label lbSigla;

    @FXML
    private TextField txtChefia;

    @FXML
    private Label lbTelefone;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private TableColumn<?, ?> tableColumnChefia;

    @FXML
    private Button btnAlterar;

    @FXML
    private Label lbNome;

    @FXML
    private TabPane tabPane;
    
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
