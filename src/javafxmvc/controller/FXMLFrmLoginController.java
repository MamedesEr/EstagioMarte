package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafxmvc.Principal;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Usuario;
import javax.swing.JOptionPane;

/**
 *
 * @author Mattheus
 */
public class FXMLFrmLoginController implements Initializable{
    
    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private ImageView imgGrupo;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEntrar;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    static int id_usuario;

    public Integer retornaID(){
        return id_usuario; 
    }
    
    void chama_tela_login() throws IOException{
        Stage stage = new Stage();
        Image applicationIcon = new Image(getClass().getResourceAsStream("/javafxmvc/imagem/ifmt.png"));
        stage.getIcons().add(applicationIcon);
        Parent root = FXMLLoader.load(FXMLFrmPrincipalController.class.getClassLoader().getResource("javafxmvc/view/FrmPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Sistema Controle de chaves");
        stage.setResizable(false);
        stage.show();
        
    }
    
    @FXML
    void button_entrar(ActionEvent event) throws IOException {
        confirmaOperacao();
        
    }
    
    
    public void confirmaOperacao() throws IOException{
        PreparedStatement comando = null;
        String senha = null;
        String login = null;
       
        try {
            login = txtLogin.getText();
            senha = txtSenha.getText();
        }catch(Exception ex){
            Alert msg_erro = new Alert(Alert.AlertType.ERROR);
            msg_erro.setTitle("Erro ao logar no sistema");
            msg_erro.setHeaderText("Ação inconsistente");
            msg_erro.setContentText("Por favor corrija");
            msg_erro.showAndWait();   
        }
        if(login.length()!= 0 && senha.length() != 0){
            try {
                comando = connection.prepareStatement("SELECT * FROM Usuario WHERE login=?");    
                login = txtLogin.getText();
                senha = txtSenha.getText();
                comando.setString(1, login);
                ResultSet resultado = comando.executeQuery();
                if (resultado.next()) {
                    if (resultado.getString("senha").equals(senha)) {
                        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                        dialogoInfo.setTitle("Diálogo de informação");
                        dialogoInfo.setHeaderText("Login realizado com sucesso!!!");
                        dialogoInfo.setContentText("Bem vindo, "+resultado.getString("login")+"!");
                        dialogoInfo.showAndWait();
                        id_usuario = resultado.getInt("id_usuario");
                        chama_tela_login();
                        Principal.login.close();
                    } else {
                        Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                        dialogoErro.setTitle("Mensagem de error:");
                        dialogoErro.setHeaderText("Senha incorreta!");
                        dialogoErro.setContentText("Digite sua senha corretamente.");
                        dialogoErro.showAndWait();
                        txtSenha.setText("");
                        txtSenha.requestFocus();
                    }
                } else {
                    Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                    dialogoErro.setTitle("Mensagem de error:");
                    dialogoErro.setHeaderText("Login não encontrato!");
                    dialogoErro.setContentText("Digite um login válido.");
                    dialogoErro.showAndWait();
                    txtLogin.setText("");
                    txtSenha.setText("");
                    txtLogin.requestFocus();
                }     
            }catch (SQLException ex){
                Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                dialogoErro.setTitle("Mensagem de error:");
                dialogoErro.setHeaderText(login);
                dialogoErro.setContentText("Falha ao realizar login");
                dialogoErro.showAndWait();
            }      
        } else{
            if(login.length()!= 1 && senha.length() != 0){
              Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
              dialogoErro.setTitle("Mensagem de error:");
              dialogoErro.setHeaderText("Campo login é obrigatório!");
              dialogoErro.setContentText("Digite um login válido.");
              dialogoErro.showAndWait();
              txtLogin.setText("");
              txtSenha.setText("");  
              txtLogin.requestFocus();
            } else {
                if(login.length()!= 0 && senha.length() != 1){
                  Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
              dialogoErro.setTitle("Mensagem de error:");
              dialogoErro.setHeaderText("Campo senha é obrigatório!");
              dialogoErro.setContentText("Digite sua senha corretamente.");
              dialogoErro.showAndWait();
              txtSenha.setText(""); 
              txtSenha.requestFocus();
                }else {
                    if(login.length()!= 1 && senha.length() != 1){
                        Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                        dialogoErro.setTitle("Mensagem de error:");
                        dialogoErro.setHeaderText("Campo login e senha são obrigatórios!");
                        dialogoErro.setContentText("Digite seu login e senha corretamente.");
                        dialogoErro.showAndWait();
                        txtLogin.setText("");
                        txtSenha.setText(""); 
                        txtLogin.requestFocus();
                    }
                }
            }
        } 
    }

    @FXML
    void button_cancelar(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection(connection);
    }
}
