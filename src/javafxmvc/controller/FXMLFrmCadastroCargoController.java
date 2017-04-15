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
import javafxmvc.model.dao.CargoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cargo;


public class FXMLFrmCadastroCargoController implements Initializable{
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
    private TextArea txtAreaDescricao;

    @FXML
    private Button btnVoltar;

    @FXML
    private AnchorPane anchorPaneRodape;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableView<Cargo> tableView;

    @FXML
    private Button btnPesquisa;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TableColumn<Cargo, String> tableColumnDescricao;

    @FXML
    private Label lbCodigo;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Button btnAlterar;

    @FXML
    private TabPane tabPane;
    
    private List <Cargo> listCargos;
    private ObservableList <Cargo> observableListCargos;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CargoDAO cargoDAO = new CargoDAO();
    private Cargo cargo;
    String estado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargoDAO.setConnection(connection);
        carregarTableViewCargo();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCargo(newValue));

    }
    
    public void carregarTableViewCargo() {
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        listCargos = cargoDAO.listar();

        observableListCargos = FXCollections.observableArrayList(listCargos);
        tableView.setItems(observableListCargos);
    }
    
    public void selecionarItemTableViewCargo(Cargo cargo){ 
        if (cargo != null) {
            btnAlterar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }
    
    @FXML
    void btnPesquisar_onAction (ActionEvent evento) {
        String filtro = this.txtPesquisa.getText().trim().toUpperCase();
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        listCargos = cargoDAO.pesquisar(filtro);

        observableListCargos = FXCollections.observableArrayList(listCargos);
        tableView.setItems(observableListCargos);
        btnExcluir.setDisable(true);
        btnAlterar.setDisable(true);
    }
    
    @FXML
    void btnNovoCargo_onAction (ActionEvent evento) {
        estado = "novo";
        tabAlterar.setDisable(false);
        tabPane.getSelectionModel().select(tabAlterar);
        tabConsulta.setDisable(true);
        txtAreaDescricao.requestFocus();
    }
    
    @FXML
    void btnAlterarCargo_onAction (ActionEvent evento) {
        cargo = tableView.getSelectionModel().getSelectedItem();
        if (cargo != null) {
            estado = "alterar";
            tabAlterar.setDisable(false);
            tabPane.getSelectionModel().select(tabAlterar);
            tabConsulta.setDisable(true);
            
            txtCodigo.setText(String.valueOf(this.cargo.getIdCargo()));
            txtAreaDescricao.setText(cargo.getDescricao());
            txtAreaDescricao.requestFocus();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Cargo na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    void btnExcluirAtleta_onAction (ActionEvent evento) {
        Cargo car = tableView.getSelectionModel().getSelectedItem();
        if (car != null) {
            cargoDAO.remover(car);
            carregarTableViewCargo();
            btnExcluir.setDisable(true);
            btnAlterar.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Cargo na Tabela!");
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
        carregarTableViewCargo();
    }
    
     @FXML
    void btnSalvar_onAction (ActionEvent evento) throws IOException{
        if (validarEntradaDeDados()) {
            if(estado == "novo"){
                Cargo cargo = new Cargo();
                cargo.setDescricao(txtAreaDescricao.getText());
                cargoDAO.inserir(cargo);
                limparTxt();
            }

            if(estado == "alterar"){
                Cargo car = cargo;
                car.setDescricao(txtAreaDescricao.getText());
                cargoDAO.alterar(car);
                limparTxt();
            }
            
            carregarTableViewCargo();

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

        if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().length() == 0) {
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
    void btnVoltar_onAction() throws IOException{
        //Abrindo a tela de cadastro
        AnchorPane abrirCadastroUsuario = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FrmCadastro.fxml"));
        anchorPane.getChildren().setAll(abrirCadastroUsuario);    
    }
    
    public void limparTxt(){
        txtCodigo.setText("");
        txtAreaDescricao.setText("");
    }
    
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
}
