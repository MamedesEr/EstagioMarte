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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;

/**
 *
 * @author Mattheus
 */
public class FXMLFrmPrincipalController implements Initializable{
    
    @FXML
    private Button btnEmprestimo;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnDevolucao;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnManutencao;

    @FXML
    private TableView<Chave> tableViewChaves;

    @FXML
    private Button btnPesquisar;

    @FXML
    private TableColumn<Chave, String> tableColumnChaves;

    @FXML
    private TableColumn<Chave, String> tableColumnStatus;
    
    private List <Chave> listChaves;
    private ObservableList <Chave> observableListChaves;
    private Stage dialogStage;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ChaveDAO chaveDAO = new ChaveDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private Chave chave;
    String estado;
    
    
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
            btnEmprestimo.setDisable(false);
            btnDevolucao.setDisable(false);
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
        Chave chave = new Chave();
        boolean buttonConfirmarClicked = showFXMLFrmEmprestimoDialog(emprestimo);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                emprestimoDAO.setConnection(connection);
                emprestimoDAO.inserir(emprestimo);
                String status = "Indisponivel";
                chave.setStatus(status);
                chaveDAO.setConnection(connection);
                chaveDAO.inserir(chave);
                connection.commit();
                carregarTableViewChave();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLFrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLFrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
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
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        // Setando a Venda no Controller.
        FXMLFrmEmprestimoDialog controller = loader.getController();
        controller.setDialogStage(dialogStage);
        //controller.set(emprestimo);
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
        return controller.isButtonConfirmarClicked();
    }
}
