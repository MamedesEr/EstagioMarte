
package visao;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;





public class LoginFXMLController implements Initializable {

    
     //====================Componentes Visuais===========

    @FXML
    private TextField CampoNome;
    
    @FXML
    private TextField CampoSenha;
    
    
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
    private void LoginOnAction (ActionEvent event){
        this.Login();
    }

    
}
