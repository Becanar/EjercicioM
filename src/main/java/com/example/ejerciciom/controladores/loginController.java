package com.example.ejerciciom.controladores;

import com.example.ejerciciom.dao.userDao;
import com.example.ejerciciom.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que se encarga de controlar el login
 */
public class loginController {

    @FXML
    private Button btLogin;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsuario;

    @FXML
    private GridPane rootPane;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsuario;

    /**
     * Maneja el evento de inicio de sesión cuando el usuario presiona el botón de login.
     * Valida las credenciales del usuario e inicia la ventana de la aplicación si son correctas.
     *
     * @param event El evento de acción que se dispara al presionar el botón de login.
     */
    @FXML
    void login(ActionEvent event) {
        ArrayList<String> lst = new ArrayList<>();
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (usuario.equals("")) {
            lst.add("El usuario no puede estar vacío.");
        }
        if (password.equals("")) {
            lst.add("La contraseña no puede estar vacía.");
        }
        if (!lst.isEmpty()) {
            error(lst);
        } else {
            User user = userDao.getUsuario(usuario);
            if (user == null) {
                lst.add("Usuario no válido.");
                txtUsuario.setText("");
                txtPassword.setText("");
                error(lst);
            } else {
                if (password.equals(user.getPassword())) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(loginController.class.getResource("/com/example/ejerciciom/fxml/aeropuertos.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        try {
                            Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
                            stage.getIcons().add(img);
                        } catch (Exception e) {
                            lst.add("Error al cargar la imagen.");
                            error(lst);
                        }
                        scene.getStylesheets().add(getClass().getResource("/com/example/ejerciciom/estilo/style.css").toExternalForm());
                        stage.setTitle("AVIONES - AEROPUERTOS");
                        stage.show();
                        Stage esta = (Stage) txtUsuario.getScene().getWindow();
                        esta.close();

                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        lst.add("No se ha podido abrir la ventana.");
                        error(lst);
                    }
                } else {
                    lst.add("Contraseña incorrecta");
                    txtPassword.setText("");
                    error(lst);
                }
            }
        }
    }

    /**
     * Muestra una alerta con un mensaje de error específico.
     *
     * @param lst Lista de mensajes de error a mostrar en la alerta.
     */
    private void error(ArrayList<String> lst) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(btLogin.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle("Error");
        String error = String.join("\n", lst);
        alert.setContentText(error);
        alert.showAndWait();
    }
}
