package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;
import javafxmvc.model.domain.Usuario;


public class FXMLFrmPrincipalController2 implements Initializable{
    
    @FXML
    private Button btnEmprestimo;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private AnchorPane anchorPaneM;

    @FXML
    private Button btnDevolucao;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnManutencao;
    
    @FXML
    private Button btnAjuda;

    @FXML
    private TableView<Chave> tableViewChaves;

    @FXML
    private Button btnPesquisar;

    @FXML
    private TableColumn<Chave, String> tableColumnChaves;

    @FXML
    private TableColumn<Chave, String> tableColumnStatus;
    
    @FXML
    private Tab tabManutencao;
    
    @FXML
    private TabPane tbpPrincipal;
    
    @FXML
    private Tab tabAjuda;
    
    @FXML
    private Tab tabControle;
    
    @FXML
    private Text labelSistema;

    @FXML
    private Text labelDesen;

    @FXML
    private ImageView imgDanca;
    
    @FXML
    private Button btnPessoa;

    @FXML
    private Button btnChave;

    @FXML
    private Button btnDepartamento;

    @FXML
    private Button btnVoltar;
    
    @FXML
    private Button btnVoltar1;
    
    @FXML
    private Button btnManual;

    @FXML
    private Button btnCargo;

    @FXML
    private Button btnUsuario;
    
    private List <Chave> listChaves;
    private ObservableList <Chave> observableListChaves;
    private Stage dialogStage;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ChaveDAO chaveDAO = new ChaveDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    String estado;
    static int id_chave;
    int id_usuario;
    
    public Integer retornaIdChave(){
        return id_chave;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chaveDAO.setConnection(connection);
        carregarTableViewChave();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewChaves.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewChaves(newValue));
    }
    
    public void carregarTableViewChave() {
        tableColumnChaves.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listChaves = chaveDAO.listar();

        observableListChaves = FXCollections.observableArrayList(listChaves);
        tableViewChaves.setItems(observableListChaves);
    }
    
    public void selecionarItemTableViewChaves(Chave chave){
        if (chave != null) {
            if(chave.getStatus().equals("Indisponível")){
                btnDevolucao.setDisable(false);
                btnEmprestimo.setDisable(true);
            }
            if(chave.getStatus().equals("Disponível")){
                btnEmprestimo.setDisable(false);
                btnDevolucao.setDisable(true);
            }
            id_chave = chave.getIdChave();
        }
    }
    
    @FXML
    void btnPesquisar_onAction (ActionEvent evento) {
        String filtro = this.txtPesquisar.getText().trim().toUpperCase();
        tableColumnChaves.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listChaves = chaveDAO.pesquisar(filtro);

        observableListChaves = FXCollections.observableArrayList(listChaves);
        tableViewChaves.setItems(observableListChaves);
        btnEmprestimo.setDisable(true);
        btnDevolucao.setDisable(true);
    }
    
    @FXML
    void btnSair_onAction (ActionEvent evento) {
        System.exit(0);
    }
    
    @FXML
    void btnEmprestimo_onAction (ActionEvent evento) throws IOException {
        Emprestimo emprestimo = new Emprestimo();
        boolean buttonConfirmarClicked = showFXMLFrmEmprestimoDialog(emprestimo);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                connection.commit();
                carregarTableViewChave();
                btnEmprestimo.setDisable(true);
                btnDevolucao.setDisable(true);
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLFrmPrincipalController2.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLFrmPrincipalController2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    void btnDevolucao_onAction (ActionEvent evento) throws IOException {
        Emprestimo emprestimo = new Emprestimo();
        boolean buttonConfirmarClicked = showFXMLFrmDevolucaoDialog(emprestimo);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                connection.commit();
                carregarTableViewChave();
                btnEmprestimo.setDisable(true);
                btnDevolucao.setDisable(true);
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLFrmPrincipalController2.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLFrmPrincipalController2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean showFXMLFrmEmprestimoDialog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLFrmEmprestimoDialog.class.getResource("/javafxmvc/view/FrmEmprestimoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Emprestimo");
        Image applicationIcon = new Image(getClass().getResourceAsStream("/javafxmvc/imagem/ifmt.png"));
        dialogStage.getIcons().add(applicationIcon);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        // Setando o emprestimo no Controller.
        FXMLFrmEmprestimoDialog controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEmprestimo(emprestimo);
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
        return controller.isButtonConfirmarClicked();
    }
    
    public boolean showFXMLFrmDevolucaoDialog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLFrmDevolucaoDialog.class.getResource("/javafxmvc/view/FrmDevolucaoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Devolução");
        Image applicationIcon = new Image(getClass().getResourceAsStream("/javafxmvc/imagem/ifmt.png"));
        dialogStage.getIcons().add(applicationIcon);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        // Setando a devolução no Controller.
        FXMLFrmDevolucaoDialog controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEmprestimo(emprestimo);
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
        return controller.isButtonConfirmarClicked();
    }
    
    @FXML
    void btnManutencao_onAction (ActionEvent evento) throws IOException {
        tbpPrincipal.getSelectionModel().select(tabManutencao);
        tabManutencao.setDisable(false);
        tabAjuda.setDisable(true);
        tabControle.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
    }
    
    @FXML
    void btnAjuda_onAction (ActionEvent evento) throws IOException {
        tbpPrincipal.getSelectionModel().select(tabAjuda);
        tabAjuda.setDisable(false);
        tabManutencao.setDisable(true);
        tabControle.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
    }
    
    @FXML
    void btnVoltar_onAction (ActionEvent evento) throws IOException {
        tbpPrincipal.getSelectionModel().select(tabControle);
        tabControle.setDisable(false);
        tabAjuda.setDisable(true);
        tabManutencao.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
        carregarTableViewChave();
    }
}
