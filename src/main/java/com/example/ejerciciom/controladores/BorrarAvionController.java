package com.example.ejerciciom.controladores;

import com.example.ejerciciom.dao.aeropuertoDao;
import com.example.ejerciciom.dao.avionDao;
import com.example.ejerciciom.model.Aeropuerto;
import com.example.ejerciciom.model.Avion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * La clase {@code BorrarAvionController} se encarga de manejar la lógica
 * para la interfaz de usuario que permite borrar un avión. Esta clase se
 * encarga de cargar los aeropuertos y aviones disponibles, así como
 * gestionar las acciones del usuario.
 */
public class BorrarAvionController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btGuardar;

    @FXML
    private ComboBox<Aeropuerto> comboAeropuerto;

    @FXML
    private ComboBox<Avion> comboAvion;

    @FXML
    private Label lblAeropuertos;

    @FXML
    private Label lblAviones;

    @FXML
    private Label lblBorrar;

    @FXML
    private FlowPane panelBotones;

    @FXML
    private GridPane rootPane;

    /**
     * Inicializa el controlador. Carga la lista de aeropuertos y
     * establece un oyente para el cambio de selección en el
     * ComboBox de aeropuertos.
     *
     * @param url la ubicación de la raíz del objeto de control
     * @param resourceBundle el conjunto de recursos que contiene
     *                      las propiedades de localización
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btGuardar.setDefaultButton(true);
        btCancelar.setCancelButton(true);
        ObservableList<Aeropuerto> aeropuertos = aeropuertoDao.cargarListado();
        comboAeropuerto.setItems(aeropuertos);
        comboAeropuerto.getSelectionModel().select(0);
        comboAeropuerto.valueProperty().addListener(new ChangeListener<Aeropuerto>() {
            @Override
            public void changed(ObservableValue<? extends Aeropuerto> observableValue, Aeropuerto oldValue, Aeropuerto newValue) {
                cambioAeropuerto(newValue);
            }
        });
        cambioAeropuerto(comboAeropuerto.getSelectionModel().getSelectedItem());
    }

    /**
     * Cambia la lista de aviones mostrados en función del aeropuerto seleccionado.
     *
     * @param aeropuerto el aeropuerto seleccionado
     */
    public void cambioAeropuerto(Aeropuerto aeropuerto) {
        if (aeropuerto != null) {
            ObservableList<Avion> aviones = avionDao.cargarListado(aeropuerto);
            comboAvion.setItems(aviones);
            comboAvion.getSelectionModel().select(0);
        }
    }

    /**
     * Maneja el evento de cancelar. Cierra la ventana actual.
     *
     * @param event el evento de acción que desencadena este método
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)comboAeropuerto.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja el evento de guardar. Elimina el avión seleccionado y muestra
     * un mensaje de confirmación o error.
     *
     * @param event el evento de acción que desencadena este método
     */
    @FXML
    void guardar(ActionEvent event) {
        Avion avion = comboAvion.getSelectionModel().getSelectedItem();
        boolean resultado = avionDao.eliminar(avion);
        if (resultado) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("Avion eliminado correctamente");
            confirmacion(lst);
            Stage stage = (Stage)comboAeropuerto.getScene().getWindow();
            stage.close();
        } else {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("No se ha podido eliminar el avión.");
            alerta(lst);
        }
    }

    /**
     * Muestra una alerta de error con los mensajes proporcionados.
     *
     * @param textos una lista de mensajes a mostrar en la alerta
     */
    public void alerta(ArrayList<String> textos) {
        String contenido = String.join("\n", textos);
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta de información con los mensajes proporcionados.
     *
     * @param textos una lista de mensajes a mostrar en la alerta
     */
    public void confirmacion(ArrayList<String> textos) {
        String contenido = String.join("\n", textos);
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
