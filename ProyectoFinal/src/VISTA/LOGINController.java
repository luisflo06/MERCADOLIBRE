/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package VISTA;

import PRODUCTO.Conexion;
import java.sql.Connection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LUIS ANGEL FLOREZ
 */
public class LOGINController implements Initializable {

    @FXML
    private TextField txt_user;
    @FXML
    private Button btningresar;
    @FXML
    private Hyperlink link_olvpasw;
    @FXML
    private PasswordField txt_pasw;
    @FXML
    private TextField txt_userre;
    @FXML
    private Button btnregistrar;
    @FXML
    private ComboBox<String> combo_pregun;
    @FXML
    private PasswordField txt_paswre;
    @FXML
    private TextField res_combo;
    @FXML
    private Button nv_cuen;

    @FXML
    private Button cuen_ready;
    @FXML
    private AnchorPane pagbtncrear;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    List<String> opciones = new ArrayList<>();

    public void ingreboton(ActionEvent event) {

        if (txt_user.getText().isEmpty() || txt_pasw.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MENSAJE ERROR");
            alert.setHeaderText(null);
            alert.setContentText("SU USUARIO O CONTRASEÑA SON INCORRECTOS");

        } else {

            String Selecdata = "SELECT usuario, contraseña FROM registro WHERE usuario = ? and contraseña = ?";

            connect = Conexion.connectDB();

            try {

                prepare = connect.prepareStatement(Selecdata);
                prepare.setString(1, txt_user.getText());
                prepare.setString(2, txt_pasw.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    
                  
                    
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("MENSAJE INFORMATIVO");
                    alert.setHeaderText(null);
                    alert.setContentText("INGRESO EXITOSO");
                    alert.showAndWait();
                    
                    
                    Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene (root);
                    stage.setTitle("ELECTRIUM.INC");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    stage.setScene(scene);
                    stage.show();
                    
                    btningresar.getScene().getWindow().hide();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("MENSAJE ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("USUARIO/CONTRASEÑA SON INCORRECTOS");
                    alert.showAndWait();

                }

            } catch (Exception e) {e.printStackTrace();
            }

        }

    }

    public void cambiopagina(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == nv_cuen) {

            slider.setNode(pagbtncrear);
            slider.setToX(300);
            slider.setDuration(javafx.util.Duration.seconds(.5));

            slider.setOnFinished((t) -> {

                cuen_ready.setVisible(true);
                nv_cuen.setVisible(false);

            });

            slider.play();

        } else if (event.getSource() == cuen_ready) {
            slider.setNode(pagbtncrear);
            slider.setToX(0);
            slider.setDuration(javafx.util.Duration.seconds(.5));

            slider.setOnFinished((t) -> {

                cuen_ready.setVisible(false);
                nv_cuen.setVisible(true);

            });

            slider.play();

        }

    }

    public void regbtn() {

        if (txt_userre.getText().isEmpty() || txt_paswre.getText().isEmpty()
                || combo_pregun.getSelectionModel().getSelectedItem() == null || txt_userre.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("MENSAJE ERROR");
            alert.setHeaderText(null);
            alert.setContentText("FAVOR LLENAR LAS CASILLAS EN BLANCO");
            alert.showAndWait();

        } else {

            String reginfo = "INSERT INTO  registro (usuario,contraseña,pregunta,respuesta,date) " + "VALUES (?,?,?,?,?)";

            connect = Conexion.connectDB();

            try {

                String checkuser = "SELECT usuario FROM registro WHERE usuario = '" + txt_userre.getText() + "'";

                prepare = connect.prepareStatement(checkuser);
                result = prepare.executeQuery();
                if (result.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("MENSAJE ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText(txt_userre.getText() + "SU USUARIO ESTA REGISTRADO");
                    alert.showAndWait();

                } else if (txt_paswre.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("MENSAJE ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("POR FAVOR INGRESE UNA CONTRASEÑA DE AL MENOS 8 CARACTERES");
                    alert.showAndWait();

                } else {
                    prepare = connect.prepareStatement(reginfo);
                    prepare.setString(1, txt_userre.getText());
                    prepare.setString(2, txt_paswre.getText());
                    prepare.setString(3, (String) combo_pregun.getSelectionModel().getSelectedItem());
                    prepare.setString(4, res_combo.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("MENSAJE INFORMATIVO");
                    alert.setHeaderText(null);
                    alert.setContentText("¡USUARIO REGISTRADO CON EXITO!");
                    alert.showAndWait();

                    txt_user.setText("");
                    txt_paswre.setText("");
                    combo_pregun.getSelectionModel().clearSelection();
                    res_combo.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(pagbtncrear);
                    slider.setToX(0);
                    slider.setDuration(javafx.util.Duration.seconds(.5));

                    slider.setOnFinished((t) -> {

                        cuen_ready.setVisible(false);
                        nv_cuen.setVisible(true);

                    });

                    slider.play();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        opciones.add("¿CUANDO CUMPLES AÑOS?");
        opciones.add("¿EN QUE LUGAR NACISTE?");
        opciones.add("¿CUANTOS AÑOS TIENES?");

        combo_pregun.setItems(FXCollections.observableArrayList(opciones));
        // TODO
    }

}
