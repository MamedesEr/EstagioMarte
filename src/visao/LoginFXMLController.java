
package visao;


import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class LoginFXMLController implements Initializable {

    Stage PalcoOrigem;
     //====================Componentes Visuais===========
    @FXML
    private Button ButtonLogin;
    
    @FXML
    private TextField CampoNome;
    
    @FXML
    private TextField CampoSenha;
    
    public void setPalcoOrigem(Stage PalcoOrigem){
        this.PalcoOrigem = PalcoOrigem;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //=====================Trata a Lógica da Interface de Login=========
    public void Login(){
        
        if (CampoNome.getText().trim()  == "" || CampoSenha.getText().trim() == "") {
            JOptionPane.showInputDialog("Os Campos devem ser preenchidos");
            
        }
    }
    
    //=========================Tratamento de Eventos==================
    @FXML
    private void buttonLogin (ActionEvent event){
        this.Login();
    }
    
}
