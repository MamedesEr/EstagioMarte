package javafxmvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Mattheus Nunes
 */
public class Principal extends Application {
    
    public static void main(String[] args) {
        Application.launch(Principal.class, args);
        
    }
    
    public static Stage login;//Para fechar a Tela de Login quando abrir a tela Principal
    
    @Override
    public void start(Stage stage) {
        login = stage; //Para fechar a tela depois quando abrir a Tela Principal
        BorderPane FrmLogin = null;
        //Image applicationIcon = new Image(getClass().getResourceAsStream("imagem/teste.jpg"));
        //stage.getIcons().add(applicationIcon);
        try {
            FrmLogin = FXMLLoader.load(getClass().getResource("view/FrmLogin.fxml"));
        } catch (Exception ex) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println(ex.getMessage());  
        }

        stage.setTitle("Tela de Login do Sistema");
        Scene scene = new Scene(FrmLogin);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
}
