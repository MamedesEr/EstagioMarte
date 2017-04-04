package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.PessoaDAO;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;
import javafxmvc.model.domain.Usuario;

/**
 *
 * @author Mattheus
 */
public class FXMLFrmEmprestimoDialog implements Initializable{
    @FXML
    private TextField txtHoraEmprestimo;

    @FXML
    private Button buttonCancelar;

    @FXML
    private DatePicker datePickerDataEmprestimo;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private ComboBox comboBoxPessoa;
    
    @FXML
    private AnchorPane anchorPane;
    
    private List<Pessoa> listPessoa;
    private ObservableList<Pessoa> observableListPessoa;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Emprestimo emprestimo;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pessoaDAO.setConnection(connection);
        carregarComboBoxPessoa();
    }
    
    public void carregarComboBoxPessoa() {
        listPessoa = pessoaDAO.listar();
        observableListPessoa = FXCollections.observableArrayList(listPessoa);
        comboBoxPessoa.setItems(observableListPessoa);
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
            emprestimo.setPessoa((Pessoa)comboBoxPessoa.getSelectionModel().getSelectedItem());
            emprestimo.setDtEmprestimo(datePickerDataEmprestimo.getValue());
            emprestimo.setHrEmprestimo(Time.valueOf(txtHoraEmprestimo.getText()));
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
        if (comboBoxPessoa.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Pessoa inválida!\n";
        }
        if (datePickerDataEmprestimo.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (txtHoraEmprestimo.getText()== null) {
            errorMessage += "Hora inválida!\n";
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

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
}
