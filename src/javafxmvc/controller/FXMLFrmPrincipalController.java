package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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
    private Button btnSair;

    @FXML
    private Button btnManutencao;

    @FXML
    private TableView<?> tableViewChaves;

    @FXML
    private TableColumn<?, ?> tableColumnChaves;

    @FXML
    private TableColumn<?, ?> tableColumnStatus;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
