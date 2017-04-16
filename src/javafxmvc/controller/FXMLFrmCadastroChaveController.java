package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;

public class FXMLFrmCadastroChaveController implements Initializable{
    @FXML
    private Tab tabConsulta;

    @FXML
    private AnchorPane anchorPaneConsulta;

    @FXML
    private Label lbDescricao;

    @FXML
    private AnchorPane anchorPaneBotoes;

    @FXML
    private Label lbUsuario;

    @FXML
    private Button btnInserir;

    @FXML
    private Tab tabAlterar;

    @FXML
    private AnchorPane anchorPaneAlterar;

    @FXML
    private Label lbDigite;

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
    private Label lbStatus;

    @FXML
    private TableColumn<Chave, String> tableColumnIdentificador;

    @FXML
    private TableView<Chave> tableView;

    @FXML
    private Button btnPesquisa;

    @FXML
    private TableColumn<Chave, String> tableColumnDescricao;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDescricao;

    @FXML
    private ComboBox<Chave> comboBoxStatus;

    @FXML
    private Label lbCodigo;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Button btnAlterar;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField txtIdentificador;

    @FXML
    private Label lbIdentificador;

    @FXML
    private TableColumn<Chave, String> tableColumnStatus;
    
    private List <Chave> listChave;
    private ObservableList <Chave> observableListchaves;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ChaveDAO chaveDAO = new ChaveDAO();
    private Chave chave;
    String estado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chaveDAO.setConnection(connection);
        carregarTableViewChave();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewChave(newValue));

    }
    
    public void carregarTableViewChave() {
        tableColumnIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        listChave = chaveDAO.listar();

        observableListchaves = FXCollections.observableArrayList(listChave);
        tableView.setItems(observableListchaves);
    }
    
    public void selecionarItemTableViewChave(Chave chave){ 
        if (chave != null) {
            btnAlterar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }
    
    @FXML
    void btnPesquisar_onAction (ActionEvent evento) {
        String filtro = this.txtPesquisa.getText().trim().toUpperCase();
        tableColumnIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listChave = chaveDAO.pesquisar(filtro);

        observableListchaves = FXCollections.observableArrayList(listChave);
        tableView.setItems(observableListchaves);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
    }
    
    @FXML
    void btnNovoChave_onAction (ActionEvent evento) {
        estado = "novo";
        tabAlterar.setDisable(false);
        tabPane.getSelectionModel().select(tabAlterar);
        tabConsulta.setDisable(true);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
        txtIdentificador.requestFocus();
    }
    
    @FXML
    void btnAlterarChave_onAction (ActionEvent evento) {
        chave = tableView.getSelectionModel().getSelectedItem();
        if (chave != null) {
            estado = "alterar";
            tabAlterar.setDisable(false);
            tabPane.getSelectionModel().select(tabAlterar);
            tabConsulta.setDisable(true);
            btnExcluir.setDisable(true);
            btnInserir.setDisable(true);
            
            txtCodigo.setText(String.valueOf(this.chave.getIdChave()));
            txtIdentificador.setText(chave.getIdentificador());
            txtDescricao.setText(chave.getDescricao());
            txtIdentificador.requestFocus();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Departamento na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    void btnExcluirChave_onAction (ActionEvent evento) {
        Chave ch = tableView.getSelectionModel().getSelectedItem();
        if (ch != null) {
            chaveDAO.remover(ch);
            carregarTableViewChave();
            btnExcluir.setDisable(true);
            btnAlterar.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Chave na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    void btnCancelar_onAction (ActionEvent evento) {
        tabConsulta.setDisable(false);
        tabPane.getSelectionModel().select(tabConsulta);
        tabAlterar.setDisable(true);
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
        btnInserir.setDisable(false);
        limparTxt();
        carregarTableViewChave();
    }
    
    @FXML
    void btnSalvar_onAction (ActionEvent evento) throws IOException{
        if (validarEntradaDeDados()) {
            if(estado == "novo"){
                Chave chave = new Chave();
                chave.setIdentificador(txtIdentificador.getText());
                chave.setStatus("Disponível");
                chave.setDescricao(txtDescricao.getText());
                chaveDAO.inserir(chave);
                limparTxt();
            }

            if(estado == "alterar"){
                Chave cha = chave;
                cha.setIdentificador(txtIdentificador.getText());
                cha.setDescricao(txtDescricao.getText());
                chaveDAO.alterar(cha);
                limparTxt();
            }
            
            carregarTableViewChave();

            tabPane.getSelectionModel().select(tabConsulta);
            tabAlterar.setDisable(true);
            tabConsulta.setDisable(false);
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
        }
    }
    
     //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (txtIdentificador.getText() == null || txtIdentificador.getText().length() == 0) {
            errorMessage += "Chave inválida!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    public void limparTxt(){
        txtCodigo.setText("");
        txtIdentificador.setText("");
        txtDescricao.setText("");
    }
    
    @FXML
    void btnVoltar_onAction() throws IOException {
        //Abrindo a tela de cadastro
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastro.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);     
    }
        
}
