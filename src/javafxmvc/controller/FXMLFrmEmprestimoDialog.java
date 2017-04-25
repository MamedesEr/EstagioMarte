package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.dao.PessoaDAO;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Pessoa;
import javafxmvc.model.domain.TextFieldFormatter;
import javafxmvc.model.domain.Usuario;


public class FXMLFrmEmprestimoDialog implements Initializable{
    
    @FXML
    private TextField txtHoraEmprestimo;

    @FXML
    private TextField txtHoraPrevista;

    @FXML
    private DatePicker datePickerDataPrevista;

    @FXML
    private Button buttonCancelar;

    @FXML
    private DatePicker datePickerDataEmprestimo;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private TextArea txtDescricao;

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
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    Emprestimo emprestimo;
    private int id_emprestimo;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pessoaDAO.setConnection(connection);
        carregarComboBoxPessoa();
        LocalDate dt = LocalDate.now();
        LocalTime hr = LocalTime.now();
        //String dataAtual = dt.getDayOfMonth() + "/" + dt.getMonth() + "/" + dt.getYear();
        String horaAtual = hr.getHour() + ":" + hr.getMinute() + ":" + hr.getSecond();
        datePickerDataEmprestimo.setValue(dt);
        datePickerDataPrevista.setValue(dt);
        txtHoraEmprestimo.setText(horaAtual);
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
            emprestimo.setDescricao(txtDescricao.getText());
            emprestimo.setDtPrevisaoEntrega(datePickerDataPrevista.getValue());
            emprestimo.setHrPrevisaoEntrega(Time.valueOf(txtHoraPrevista.getText()));
            
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
            
            emprestimoDAO.setConnection(connection);
            emprestimoDAO.inserir(emprestimo);
            
            pegarUltimoId();
            
            String status = "Indisponível";
            chave.setStatus(status);
            chave.setId_emprestimo(id_emprestimo);
            chaveDAO.alterar(chave);
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        } 
    }
    
    public void pegarUltimoId(){
        PreparedStatement comando = null;
        try {
            comando = connection.prepareStatement("SELECT * FROM emprestimo\n" +
                "WHERE id_emprestimo = (SELECT MAX(id_emprestimo) FROM emprestimo)");
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                id_emprestimo = resultado.getInt("id_emprestimo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFrmEmprestimoDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }
    
    @FXML
    public void tfHoraRetiradaReleased(){
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##:##:##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtHoraEmprestimo);
        tff.formatter();
    }
    
    @FXML
    public void tfHoraPrevistaRelased(){
    TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##:##:##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtHoraPrevista);
        tff.formatter();    
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
        if (txtHoraEmprestimo.getText()== null || txtHoraEmprestimo.getText().length() == 0) {
            errorMessage += "Hora do empréstimo inválido!\n";
        }
        if (txtHoraPrevista.getText()== null || txtHoraPrevista.getText().length() == 0) {
            errorMessage += "Hora prevista de devolução inválida!\n";
        }        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Emprestimo");
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

    private void TextFieldFormatter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
