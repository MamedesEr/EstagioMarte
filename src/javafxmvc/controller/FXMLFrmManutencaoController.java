package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author Mattheus
 */
public class FXMLFrmManutencaoController implements Initializable{
    
    @FXML
    private Button btnPessoa;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnChave;

    @FXML
    private Button btnDepartamento;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCargo;

    @FXML
    private Button btnUsuario;

    @FXML
    void btnVoltar_onAction (ActionEvent evento) throws IOException {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
