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
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cargo;
import javafxmvc.model.domain.Departamento;
import javafxmvc.model.domain.Usuario;

public class FXMLFrmCadastroUsuarioController implements Initializable{
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
    private TableColumn<Usuario, String> tableColumnLogin;

    @FXML
    private Label lbCargo;

    @FXML
    private TextField txtSenha;

    @FXML
    private Label lbSenha;

    @FXML
    private TableColumn<Usuario, String> tableColumnNome;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnPesquisa;

    @FXML
    private TableColumn<Usuario, String> tableColumnTelefone;
    
    @FXML
    private TableColumn<Usuario, String> tableColumnCpf;

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
    private ComboBox comboBoxCargo;
    
    private List <Usuario> listUsuario;
    private ObservableList <Usuario> observableListUsuarios;
    
    private List <Departamento> listDepartamento;
    private ObservableList <Departamento> observableListDepartamentos;
    
    private List <Cargo> listCargo;
    private ObservableList <Cargo> observableListCargos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private final CargoDAO cargoDAO = new CargoDAO();
    private Usuario usuario;
    String estado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection(connection);
        departamentoDAO.setConnection(connection);
        cargoDAO.setConnection(connection);
        carregarTableViewUsuario();
        carregarComboBoxDepartamento();
        carregarComboBoxCargo();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewUsuario(newValue));

    }
    
    public void carregarTableViewUsuario() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        
        listUsuario = usuarioDAO.listar();

        observableListUsuarios = FXCollections.observableArrayList(listUsuario);
        tableView.setItems(observableListUsuarios);
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
    
    public void selecionarItemTableViewUsuario(Usuario usuario){ 
        if (usuario != null) {
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
        tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

        listUsuario = usuarioDAO.pesquisar(filtro);

        observableListUsuarios = FXCollections.observableArrayList(listUsuario);
        tableView.setItems(observableListUsuarios);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
    }
    
    @FXML
    void btnNovoUsuario_onAction (ActionEvent evento) {
        estado = "novo";
        tabAlterar.setDisable(false);
        tabPane.getSelectionModel().select(tabAlterar);
        tabConsulta.setDisable(true);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
        txtLogin.requestFocus();
    }
    
    @FXML
    void btnAlterarUsuario_onAction (ActionEvent evento) {
        usuario = tableView.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            estado = "alterar";
            tabAlterar.setDisable(false);
            tabPane.getSelectionModel().select(tabAlterar);
            tabConsulta.setDisable(true);
            btnExcluir.setDisable(true);
            btnInserir.setDisable(true);
     
            txtCodigo.setText(String.valueOf(this.usuario.getIdUsuario()));
            txtNome.setText(usuario.getNome());
            txtCPF.setText(usuario.getCpf());
            txtTelefone.setText(usuario.getTelefone());
            txtEmail.setText(usuario.getEmail());
            txtLogin.setText(usuario.getLogin());
            txtSenha.setText(usuario.getSenha());
            comboBoxDepartamento.setValue(this.usuario.getDepartamento());
            comboBoxCargo.setValue(this.usuario.getCargo());
            txtLogin.requestFocus();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Usuario na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    void btnExcluirUsuario_onAction (ActionEvent evento) {
        Usuario us = tableView.getSelectionModel().getSelectedItem();
        if (us != null) {
            usuarioDAO.remover(us);
            carregarTableViewUsuario();
            btnExcluir.setDisable(true);
            btnAlterar.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Usuario na Tabela!");
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
        carregarTableViewUsuario();
    }
    
    @FXML
    void btnSalvar_onAction (ActionEvent evento) throws IOException{
        if (validarEntradaDeDados()) {
            if(estado == "novo"){
                Usuario usuario = new Usuario();
                usuario.setNome(txtNome.getText());
                usuario.setCpf(txtCPF.getText());
                usuario.setTelefone(txtTelefone.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setLogin(txtLogin.getText());
                usuario.setSenha(txtSenha.getText());
                usuario.setDepartamento((Departamento)comboBoxDepartamento.getSelectionModel().getSelectedItem());
                usuario.setCargo((Cargo)comboBoxCargo.getSelectionModel().getSelectedItem());
                usuarioDAO.inserir(usuario);
                limparTxt();
            }

            if(estado == "alterar"){
                Usuario usu = usuario;
                usu.setNome(txtNome.getText());
                usu.setCpf(txtCPF.getText());
                usu.setTelefone(txtTelefone.getText());
                usu.setEmail(txtEmail.getText());
                usu.setLogin(txtLogin.getText());
                usu.setSenha(txtSenha.getText());
                usu.setDepartamento((Departamento)comboBoxDepartamento.getSelectionModel().getSelectedItem());
                usu.setCargo((Cargo)comboBoxCargo.getSelectionModel().getSelectedItem());
                usuarioDAO.alterar(usu);
                limparTxt();
            }
            
            carregarTableViewUsuario();

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
            errorMessage += "Nome Usuario inválido!\n";
        }
        if (txtLogin.getText() == null || txtLogin.getText().length() == 0) {
            errorMessage += "Login Usuario inválido!\n";
        }
        if (txtSenha.getText() == null || txtSenha.getText().length() == 0) {
            errorMessage += "Senha Usuario inválido!\n";
        }
        if (comboBoxDepartamento.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Departamento inválido!\n";
        }
        if (comboBoxCargo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cargo inválido!\n";
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
        comboBoxDepartamento.setValue("");
        comboBoxCargo.setValue("");
    }
}
