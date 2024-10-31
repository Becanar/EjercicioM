package com.example.ejerciciom.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    /**
     * Método start, que inicializa y muestra la ventana principal de la aplicación.
     *
     * @param stage El escenario principal en el que se mostrará la interfaz.
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/example/ejerciciom/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        try {
            Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
            stage.getIcons().add(img);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }

        stage.setTitle("AVIONES-LOGIN");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Método main que lanza la aplicación.a
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}