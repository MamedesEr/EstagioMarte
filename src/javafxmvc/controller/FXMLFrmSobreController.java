package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Mattheus
 */
public class FXMLFrmSobreController implements Initializable{
    
    @FXML
    private Text labelSistema;

    @FXML
    private Text labelDesen;

    @FXML
    private ImageView imgDanca;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
