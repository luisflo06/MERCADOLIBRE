/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package VISTA;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author LUIS ANGEL FLOREZ
 */
public class EJECUTABLE extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = null;

        
            root = FXMLLoader.load(getClass().getResource("LOGIN.fxml"));
       
            
            primaryStage.setTitle("INICIO DE SESION ELECTRIUM.INC");
            
            
            
        Scene escena = new Scene(root);
        primaryStage.setScene(escena);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
