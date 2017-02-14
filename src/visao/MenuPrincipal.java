/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Junior
 */
public class MenuPrincipal extends Application{
    
   @Override
    public void start(Stage stage) throws IOException {
        Stage stageTemp = new Stage(StageStyle.UNIFIED);
        Parent parent = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
        Scene scene = new Scene(parent);
        stageTemp.setScene(scene);
        stageTemp.setTitle("Menu Principal");
        stageTemp.setResizable(false);
        stageTemp.showAndWait();
    }   
    public static void main(String[] args) {
        launch(args);
    }
}
