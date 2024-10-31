package com.example.ejerciciom.controladores;

import com.example.ejerciciom.dao.aeropuertoDao;
import com.example.ejerciciom.dao.avionDao;
import com.example.ejerciciom.model.Aeropuerto;
import com.example.ejerciciom.model.Avion;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * clase que controla añadir avión
 */
public class AniadirAvionController implements Initializable {

    @FXML
    private RadioButton btActivado;

    @FXML
    private Button btCancelar;

    @FXML
    private RadioButton btDesactivado;

    @FXML
    private Button btGuardar;

    @FXML
    private ComboBox<Aeropuerto> comboAeropuerto;

    @FXML
    private Label lblAeropuerto;

    @FXML
    private Label lblAsiento;

    @FXML
    private Label lblDatos;

    @FXML
    private Label lblModelo;

    @FXML
    private Label lblVelMax;

    @FXML
    private FlowPane panelBotones;

    @FXML
    private ToggleGroup rbGroup;

    @FXML
    private GridPane rootPane;

    @FXML
    private TextField txtAsiento;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtVelMax;

    /**
     * Inicializa el controlador, cargando la lista de aeropuertos en el ComboBox
     * y seleccionando el primer aeropuerto por defecto.
     *
     * @param url             La URL de la ubicación del recurso.
     * @param resourceBundle  El conjunto de recursos que contiene las propiedades.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Aeropuerto> aeropuertos = aeropuertoDao.cargarListado();
        comboAeropuerto.setItems(aeropuertos);
        comboAeropuerto.getSelectionModel().select(0);
    }

    /**
     * Maneja el evento de cancelación, cerrando la ventana actual.
     *
     * @param event El evento de acción que se dispara al presionar el botón de cancelar.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtAsiento.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja el evento de guardado del avión, validando la información ingresada
     * y guardando un nuevo avión si la validación es exitosa.
     *
     * @param event El evento de acción que se dispara al presionar el botón de guardar.
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        int num_asientos = 0;
        int vel_max = 0;

        if (txtModelo.getText().isEmpty()) {
            error = "El modelo del avión no puede estar vacío";
        }
        if (txtAsiento.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "El número de asientos del avión no puede estar vacío";
        } else {
            try {
                num_asientos = Integer.parseInt(txtAsiento.getText());
            } catch (NumberFormatException e) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "El número de asientos del avión tiene que ser un número entero";
            }
        }
        if (txtVelMax.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "La velocidad máxima del avión no puede estar vacío";
        } else {
            try {
                vel_max = Integer.parseInt(txtVelMax.getText());
            } catch (NumberFormatException e) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "La velocidad máxima del avión tiene que ser un número entero";
            }
        }
        if (!error.isEmpty()) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add(error);
            alerta(lst);

        } else {
            Avion avion = new Avion();
            avion.setModelo(txtModelo.getText());
            avion.setNumero_asientos(num_asientos);
            avion.setVelocidad_maxima(vel_max);
            avion.setActivado(btActivado.isSelected());
            avion.setAeropuerto(comboAeropuerto.getSelectionModel().getSelectedItem());

            ObservableList<Avion> aviones = avionDao.cargarListado();
            if (aviones.contains(avion)) {
                ArrayList<String> lst = new ArrayList<>();
                lst.add("Ya existe este modelo.");
                alerta(lst);
            } else {
                int resultado = avionDao.insertar(avion);
                if (resultado == -1) {
                    ArrayList<String> lst = new ArrayList<>();
                    lst.add("No se ha podido insertar el avión.");
                    alerta(lst);
                } else {
                    ArrayList<String> lst = new ArrayList<>();
                    lst.add("Avion insertado correctamente");
                    confirmacion(lst);
                    Stage stage = (Stage) txtAsiento.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    /**
     * Muestra una alerta con un mensaje de error específico.
     *
     * @param textos Lista de mensajes de error a mostrar en la alerta.
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
     * Muestra una alerta de confirmación con un mensaje informativo.
     *
     * @param textos Lista de mensajes de confirmación a mostrar en la alerta.
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
