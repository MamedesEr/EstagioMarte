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
import javafxmvc.model.dao.CargoDAO;
import javafxmvc.model.dao.DepartamentoDAO;
import javafxmvc.model.dao.PessoaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cargo;
import javafxmvc.model.domain.Departamento;
import javafxmvc.model.domain.Pessoa;

public class FXMLFrmCadastroPessoaController implements Initializable{
    @FXML
    private Tab tabConsulta;

    @FXML
    private AnchorPane anchorPaneConsulta;

    @FXML
    private AnchorPane anchorPaneBotoes;

    @FXML
    private Label lbUsuario;

    @FXML
    private ComboBox comboBoxDepartamento;

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
    private Label lbCargo;

    @FXML
    private TableColumn<Pessoa, String> tableColumnNome;

    @FXML
    private TableView<Pessoa> tableView;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnPesquisa;

    @FXML
    private TableColumn<Pessoa, String> tableColumnCpf;
    
    @FXML
    private TableColumn<Pessoa, String> tableColumnTelefone;

    @FXML
    private TextField txtCodigo;

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
    private ComboBox comboBoxCargo;  
    
    private List <Pessoa> listPessoa;
    private ObservableList <Pessoa> observableListPessoas;
    
    private List <Departamento> listDepartamento;
    private ObservableList <Departamento> observableListDepartamentos;
    
    private List <Cargo> listCargo;
    private ObservableList <Cargo> observableListCargos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private final CargoDAO cargoDAO = new CargoDAO();
    private Pessoa pessoa;
    String estado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pessoaDAO.setConnection(connection);
        departamentoDAO.setConnection(connection);
        cargoDAO.setConnection(connection);
        carregarTableViewPessoa();
        carregarComboBoxDepartamento();
        carregarComboBoxCargo();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewPessoa(newValue));

    }
    
    public void carregarTableViewPessoa() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        listPessoa = pessoaDAO.listar();

        observableListPessoas = FXCollections.observableArrayList(listPessoa);
        tableView.setItems(observableListPessoas);
    }
    
    public void carregarComboBoxDepartamento() {
        listDepartamento = departamentoDAO.listar();
        observableListDepartamentos = FXCollections.observableArrayList(listDepartamento);
        comboBoxDepartamento.setItems(observableListDepartamentos);
    }
    
    public void carregarComboBoxCargo() {
        listCargo = cargoDAO.listar();
        observableListCargos = FXCollections.observableArrayList(listCargo);
        comboBoxCargo.setItems(observableListCargos);
    }
    
    public void selecionarItemTableViewPessoa(Pessoa pessoa){ 
        if (pessoa != null) {
            btnAlterar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }
    
    @FXML
    void btnPesquisar_onAction (ActionEvent evento) {
        String filtro = this.txtPesquisa.getText().trim().toUpperCase();
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        listPessoa = pessoaDAO.pesquisar(filtro);

        observableListPessoas = FXCollections.observableArrayList(listPessoa);
        tableView.setItems(observableListPessoas);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
    }
    
    @FXML
    void btnNovaPessoa_onAction (ActionEvent evento) {
        estado = "novo";
        tabAlterar.setDisable(false);
        tabPane.getSelectionModel().select(tabAlterar);
        tabConsulta.setDisable(true);
        txtNome.requestFocus();
    }
    
    @FXML
    void btnAlterarPessoa_onAction (ActionEvent evento) {
        pessoa = tableView.getSelectionModel().getSelectedItem();
        if (pessoa != null) {
            estado = "alterar";
            tabAlterar.setDisable(false);
            tabPane.getSelectionModel().select(tabAlterar);
            tabConsulta.setDisable(true);
            
            txtCodigo.setText(String.valueOf(this.pessoa.getIdPessoa()));
            txtNome.setText(pessoa.getNome());
            txtCPF.setText(pessoa.getCpf());
            txtTelefone.setText(pessoa.getTelefone());
            txtEmail.setText(pessoa.getEmail());
            txtNome.requestFocus();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Pessoa na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    void btnExcluirPessoa_onAction (ActionEvent evento) {
        Pessoa pes = tableView.getSelectionModel().getSelectedItem();
        if (pes != null) {
            pessoaDAO.remover(pes);
            carregarTableViewPessoa();
            btnExcluir.setDisable(true);
            btnAlterar.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Pessoa na Tabela!");
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
        limparTxt();
        carregarTableViewPessoa();
    }
    
    @FXML
    void btnSalvar_onAction (ActionEvent evento) throws IOException{
        if (validarEntradaDeDados()) {
            if(estado == "novo"){
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(txtNome.getText());
                pessoa.setCpf(txtCPF.getText());
                pessoa.setTelefone(txtTelefone.getText());
                pessoa.setEmail(txtEmail.getText());
                pessoa.setDepartamento((Departamento)comboBoxDepartamento.getSelectionModel().getSelectedItem());
                pessoa.setCargo((Cargo)comboBoxCargo.getSelectionModel().getSelectedItem());
                pessoaDAO.inserir(pessoa);
                limparTxt();
            }

            if(estado == "alterar"){
                Pessoa pess = pessoa;
                pess.setNome(txtNome.getText());
                pess.setCpf(txtCPF.getText());
                pess.setTelefone(txtTelefone.getText());
                pess.setEmail(txtEmail.getText());
                pess.setDepartamento((Departamento)comboBoxDepartamento.getSelectionModel().getSelectedItem());
                pess.setCargo((Cargo)comboBoxCargo.getSelectionModel().getSelectedItem());
                pessoaDAO.alterar(pess);
                limparTxt();
            }
            
            carregarTableViewPessoa();

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

        if (txtNome.getText() == null || txtNome.getText().length() == 0) {
            errorMessage += "Pessoa inválida!\n";
        }
        if (comboBoxDepartamento.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Departamento inválida!\n";
        }
        if (comboBoxCargo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cargo inválida!\n";
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
        txtCPF.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        comboBoxDepartamento.getSelectionModel().clearSelection();
        comboBoxCargo.getSelectionModel().clearSelection();
    }
    
}
