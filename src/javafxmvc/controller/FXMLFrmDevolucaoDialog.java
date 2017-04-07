package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;
import javafxmvc.model.domain.Usuario;

public class FXMLFrmDevolucaoDialog implements Initializable{
    
    @FXML
    private DatePicker datePickerDataDevolucao;

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private TextField txtHoraDevolucao;

    @FXML
    private TextArea txtDescricao;
    
     //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Emprestimo emprestimo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    
    @FXML
    public void handleButtonConfirmar() {
        
        if (validarEntradaDeDados()) {
            emprestimo.setDtDevolucao(datePickerDataDevolucao.getValue());
            emprestimo.setHrDevolucao(Time.valueOf(txtHoraDevolucao.getText()));
            emprestimo.setDescricao(txtDescricao.getText());
            
            FXMLFrmLoginController id = new FXMLFrmLoginController();     
            FXMLFrmPrincipalController id_chave = new FXMLFrmPrincipalController();
            
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id.retornaID());
            
            Chave chave = new Chave();
            chave.setIdChave(id_chave.retornaIdChave());
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.setConnection(connection);
            usuario = usuarioDAO.buscar(usuario);
            
            ChaveDAO chaveDAO = new ChaveDAO();
            chaveDAO.setConnection(connection);
            chave = chaveDAO.buscar(chave);
            
            emprestimo.setUsuario(usuario);
            emprestimo.setChave(chave);
            
            String status = "Disponível";
            chave.setStatus(status);
            int id_emprestimo = chave.getId_emprestimo();
            
            emprestimo.setIdEmprestimo(id_emprestimo);
            
            emprestimoDAO.setConnection(connection);
            emprestimoDAO.alterar(emprestimo);
            
            chave.setId_emprestimo(0);
            chaveDAO.alterar(chave);
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        } 
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }
    
    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (datePickerDataDevolucao.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (txtHoraDevolucao.getText()== null) {
            errorMessage += "Hora inválida!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Devolução");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
}
