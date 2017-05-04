package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.dao.PessoaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class FXMLFrmPrincipalController implements Initializable{

    @FXML
    private CheckBox checkBoxIndispinivel;

    @FXML
    private CheckBox checkBoxDisponivel;    
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private Tab tabControle;
    
    @FXML
    private AnchorPane anchorPaneControle;
    
    @FXML
    private Tab tabManutencao;
    
    @FXML
    private AnchorPane anchorPaneManutencao;

    @FXML
    private Tab tabAjuda;
    
    @FXML
    private AnchorPane anchorPaneAjuda;    
    
    @FXML
    private Label lbChave;
    
    @FXML
    private Button btnEmprestimo;

    @FXML
    private AnchorPane anchorPane;
    
//    @FXML
//    private AnchorPane anchorPaneCentral;
    
    @FXML
    private Button btnDevolucao;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private Button btnAjuda;
    
    @FXML
    private Button btnSair;

    @FXML
    private Button btnManutencao;
    
    @FXML
    private Button btnInicio;

    @FXML
    private TableView<Chave> tableViewChaves;

    @FXML
    private Button btnPesquisar;
    
    @FXML
    private Button btnEnviar;

    @FXML
    private TableColumn<Chave, String> tableColumnChaves;
    //
    @FXML
    private TableColumn<Chave, String> tableColumnStatus;
    //*
    @FXML
    private Tooltip toolTipEnviar;

    private List <Chave> listChaves;
    private List <Emprestimo> listEmprestimos;
    private ObservableList <Chave> observableListChaves;
    private ObservableList <Emprestimo> observableListE;
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
        emprestimoDAO.setConnection(connection);
        checkBoxDisponivel.setSelected(true);
        checkBoxIndispinivel.setSelected(true);
        carregarTableViewChave();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewChaves.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewChaves(newValue));
        FXMLFrmLoginController id = new FXMLFrmLoginController();
        id_usuario = id.retornaID();
        if(id_usuario == 1){
            btnManutencao.setDisable(false);
        }else{
            btnManutencao.setDisable(true);
        }
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

        if(checkBoxDisponivel.isSelected()){
            if(checkBoxIndispinivel.isSelected()){
                listChaves = chaveDAO.pesquisar(filtro);

                observableListChaves = FXCollections.observableArrayList(listChaves);
                tableViewChaves.setItems(observableListChaves);
        
                btnEmprestimo.setDisable(true);
                btnDevolucao.setDisable(true);
            }else{
                listChaves = chaveDAO.pesquisarChaveDisponivel(filtro);

                observableListChaves = FXCollections.observableArrayList(listChaves);
                tableViewChaves.setItems(observableListChaves);
        
                btnEmprestimo.setDisable(true);
                btnDevolucao.setDisable(true);            
            }
        }else if(checkBoxIndispinivel.isSelected()){
            listChaves = chaveDAO.pesquisarChaveIndisponivel(filtro);
            
            observableListChaves = FXCollections.observableArrayList(listChaves);
            tableViewChaves.setItems(observableListChaves);
            
            btnEmprestimo.setDisable(true);
            btnDevolucao.setDisable(true);    
        } else{
            //Informar ao usuario que necessita selecionar algum status da chave para efetuar a pesquisa
            Alert dialogoErro = new Alert(Alert.AlertType.INFORMATION);
            dialogoErro.setTitle("Mensagem informativa");
            dialogoErro.setHeaderText("É necessário marcar pelo menos um status da chave");
            dialogoErro.setContentText("O status pode ser 'Chave Disponível', 'Chave Indisponível' ou ambas opções.");
            dialogoErro.showAndWait();
        }
    }
    
    @FXML
    void btnSair_onAction (ActionEvent evento) {
        System.exit(0);
    }
    
    @FXML
    void btnAjuda_onAction () throws IOException {
        //Abrindo a aba de cadastro
        tabPane.getSelectionModel().select(tabAjuda);
        AnchorPane abrirAnchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource
                                            ("/javafxmvc/view/FrmAjuda.fxml"));
        anchorPaneAjuda.getChildren().setAll(abrirAnchorPane);
        tabAjuda.setDisable(false);
        tabManutencao.setDisable(true);
        tabControle.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
        btnInicio.setDisable(false);
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
                    Logger.getLogger(FXMLFrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLFrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(FXMLFrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLFrmPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    void btnManutencao_onAction() throws IOException {
        
        //Abrindo a aba de cadastro
        tabPane.getSelectionModel().select(tabManutencao);
        AnchorPane abrirAnchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource
                                            ("/javafxmvc/view/FrmCadastro.fxml"));
        anchorPaneManutencao.getChildren().setAll(abrirAnchorPane);
        tabManutencao.setDisable(false);
        tabAjuda.setDisable(true);
        tabControle.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
        btnInicio.setDisable(false);
    }
    
    @FXML
    void btnInicio_onAction (ActionEvent evento) throws IOException {
        tabPane.getSelectionModel().select(tabControle);
        tabControle.setDisable(false);
        tabAjuda.setDisable(true);
        tabManutencao.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
        carregarTableViewChave();
        btnInicio.setDisable(true);
    }
    
    @FXML
    void btnEnviarEmail_onAction (ActionEvent evento) throws IOException, EmailException {
        //Mandar e-mail
        sendEmail();
        tabPane.getSelectionModel().select(tabControle);
        tabControle.setDisable(false);
        tabAjuda.setDisable(true);
        tabManutencao.setDisable(true);
        btnDevolucao.setDisable(true);
        btnEmprestimo.setDisable(true);
        carregarTableViewChave();
        btnInicio.setDisable(true);
    }
    
    public boolean showFXMLFrmEmprestimoDialog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLFrmEmprestimoDialog.class.getResource("/javafxmvc/view/FrmEmprestimoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Empréstimo");
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
    
    public void sendEmail() throws EmailException {
        
        String sql = "SELECT * FROM emprestimo Where dt_devolucao isnull";
        List<Emprestimo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Emprestimo emprestimo = new Emprestimo();
                Chave chave = new Chave();
                Pessoa pessoa = new Pessoa();
               
                pessoa.setIdPessoa(resultado.getInt("id_pessoa"));
                chave.setIdChave(resultado.getInt("id_chave"));

                PessoaDAO pessoaDAO = new PessoaDAO();
                pessoaDAO.setConnection(connection);                
                pessoa = pessoaDAO.buscar(pessoa);

                ChaveDAO chDAO = new ChaveDAO();
                chDAO.setConnection(connection);
                chave = chDAO.buscar(chave);

                emprestimo.setPessoa(pessoa);
                emprestimo.setChave(chave);
                retorno.add(emprestimo);
                
                SimpleEmail email = new SimpleEmail();
                //Utilize o hostname do seu provedor de email
                System.out.println("alterando hostname...");
                email.setHostName("smtp.gmail.com");
                //Quando a porta utilizada não é a padrão (gmail = 465)
                email.setSmtpPort(465);
                //Adicione os destinatários
                email.addTo(pessoa.getEmail(), pessoa.getNome());
                //Configure o seu email do qual enviará
                email.setFrom("ifmt.daee@gmail.com", "Departamento DAEE");
                //Adicione um assunto
                email.setSubject("Devolução Chave DAEE");
                //Adicione a mensagem do email
                email.setMsg("Este e-mail é para informar para devolver a chave do Laboratório: "+chave.getIdentificador());
                //Para autenticar no servidor é necessário chamar os dois métodos abaixo
                System.out.println("autenticando...");
                email.setSSL(true);
                email.setAuthentication("ifmt.daee@gmail.com", "daeeifmt2017");
                System.out.println("enviando...");
                email.send();
                System.out.println("Email enviado!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
