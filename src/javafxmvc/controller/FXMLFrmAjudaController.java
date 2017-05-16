package javafxmvc.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class FXMLFrmAjudaController {
   @FXML
    private Button btnManual;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnSobre;

    @FXML
    void btnManual_onAction(ActionEvent event) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File("manualdousuario.pdf"));
    }

    @FXML
    void btnSobre_onAction() throws IOException {
        AnchorPane abrirAnchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource
                                            ("/javafxmvc/view/FrmSobre.fxml"));
        anchorPane.getChildren().setAll(abrirAnchorPane);
    }
   
}
