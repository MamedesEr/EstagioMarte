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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafxmvc.model.dao.DepartamentoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Departamento;

public class FXMLFrmCadastroDepartamentoController implements Initializable {
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
    private TableColumn<Departamento, String> tableColumnNome;

    @FXML
    private TableView<Departamento> tableView;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnPesquisa;

    @FXML
    private TableColumn<Departamento, String> tableColumnSigla;

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
    private TableColumn<Departamento, String> tableColumnChefia;

    @FXML
    private Button btnAlterar;

    @FXML
    private Label lbNome;

    @FXML
    private TabPane tabPane;
    
    private List <Departamento> listDepartamento;
    private ObservableList <Departamento> observableListDepartamentos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private Departamento departamento;
    String estado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        departamentoDAO.setConnection(connection);
        carregarTableViewDepartamento();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewDepartemento(newValue));

    }
    
    public void carregarTableViewDepartamento() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnChefia.setCellValueFactory(new PropertyValueFactory<>("chefia"));
        tableColumnSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        
        listDepartamento = departamentoDAO.listar();

        observableListDepartamentos = FXCollections.observableArrayList(listDepartamento);
        tableView.setItems(observableListDepartamentos);
    }
    
    public void selecionarItemTableViewDepartemento(Departamento departamento){ 
        if (departamento != null) {
            btnAlterar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }
    
    @FXML
    void btnPesquisar_onAction (ActionEvent evento) {
        String filtro = this.txtPesquisa.getText().trim().toUpperCase();
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnChefia.setCellValueFactory(new PropertyValueFactory<>("chefia"));
        tableColumnSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));

        listDepartamento = departamentoDAO.pesquisar(filtro);

        observableListDepartamentos = FXCollections.observableArrayList(listDepartamento);
        tableView.setItems(observableListDepartamentos);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
    }
    
    @FXML
    void btnNovoDepartamento_onAction (ActionEvent evento) {
        estado = "novo";
        tabAlterar.setDisable(false);
        tabPane.getSelectionModel().select(tabAlterar);
        tabConsulta.setDisable(true);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
        txtNome.requestFocus();
    }
    
    @FXML
    void btnAlterarDepartamento_onAction (ActionEvent evento) {
        departamento = tableView.getSelectionModel().getSelectedItem();
        if (departamento != null) {
            estado = "alterar";
            tabAlterar.setDisable(false);
            tabPane.getSelectionModel().select(tabAlterar);
            tabConsulta.setDisable(true);
            btnExcluir.setDisable(true);
            btnInserir.setDisable(true);
            
            txtCodigo.setText(String.valueOf(this.departamento.getIdDepartamento()));
            txtNome.setText(departamento.getNome());
            txtSigla.setText(departamento.getSigla());
            txtChefia.setText(departamento.getChefia());
            txtTelefone.setText(departamento.getTelefone());
            txtEmail.setText(departamento.getEmail());
            txtAreaObservacao.setText(departamento.getObservacao());
            txtNome.requestFocus();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Departamento na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    void btnExcluirDepartamento_onAction (ActionEvent evento) {
        Departamento der = tableView.getSelectionModel().getSelectedItem();
        if (der != null) {
            departamentoDAO.remover(der);
            carregarTableViewDepartamento();
            btnExcluir.setDisable(true);
            btnAlterar.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Departamento na Tabela!");
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
        carregarTableViewDepartamento();
    }
    
    @FXML
    void btnSalvar_onAction (ActionEvent evento) throws IOException{
        if (validarEntradaDeDados()) {
            if(estado == "novo"){
                Departamento departamento = new Departamento();
                departamento.setNome(txtNome.getText());
                departamento.setSigla(txtSigla.getText());
                departamento.setChefia(txtChefia.getText());
                departamento.setTelefone(txtTelefone.getText());
                departamento.setEmail(txtEmail.getText());
                departamento.setObservacao(txtAreaObservacao.getText());
                departamentoDAO.inserir(departamento);
                limparTxt();
            }

            if(estado == "alterar"){
                Departamento derp = departamento;
                derp.setNome(txtNome.getText());
                derp.setSigla(txtSigla.getText());
                derp.setChefia(txtChefia.getText());
                derp.setTelefone(txtTelefone.getText());
                derp.setEmail(txtEmail.getText());
                derp.setObservacao(txtAreaObservacao.getText());
                departamentoDAO.alterar(derp);
                limparTxt();
            }
            
            carregarTableViewDepartamento();

            tabPane.getSelectionModel().select(tabConsulta);
            tabAlterar.setDisable(true);
            tabConsulta.setDisable(false);
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
            btnInserir.setDisable(false);
        }
    }
    
     //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (txtNome.getText() == null || txtNome.getText().length() == 0) {
            errorMessage += "Departamento inválido!\n";
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
    
    @FXML
    void btnVoltar_onAction() throws IOException {
        //Abrindo a tela de cadastro
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastro.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);    
    }
    
    public void limparTxt(){
        txtCodigo.setText("");
        txtNome.setText("");
        txtSigla.setText("");
        txtChefia.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtAreaObservacao.setText("");
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
}
