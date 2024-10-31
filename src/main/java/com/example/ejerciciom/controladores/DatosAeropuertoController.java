package com.example.ejerciciom.controladores;

import com.example.ejerciciom.dao.aeropuertoDao;
import com.example.ejerciciom.dao.aeropuertoPrivadoDao;
import com.example.ejerciciom.dao.aeropuertoPublicoDao;
import com.example.ejerciciom.dao.direccionDao;
import com.example.ejerciciom.model.Aeropuerto;
import com.example.ejerciciom.model.AeropuertoPrivado;
import com.example.ejerciciom.model.AeropuertoPublico;
import com.example.ejerciciom.model.Direccion;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar los datos de un aeropuerto, permitiendo visualizar y editar
 * tanto aeropuertos públicos como privados.
 */
public class DatosAeropuertoController implements Initializable {

    private Object aeropuerto = null;
    private Aeropuerto ap;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btGuardar;

    @FXML
    private Button btImg;

    @FXML
    private ImageView imgView;

    @FXML
    private RadioButton btPrivado;

    @FXML
    private RadioButton btPublico;

    @FXML
    private Label lblAnio;

    @FXML
    private Label lblCalle;

    @FXML
    private Label lblCapacidad;

    @FXML
    private Label lblCiudad;

    @FXML
    private Label lblDatos;

    @FXML
    private Label lblFinanciacion;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblNumTrab;

    @FXML
    private Label lblNumero;

    @FXML
    private Label lblPais;

    @FXML
    private FlowPane panelBotones;

    @FXML
    private FlowPane panelRB;

    @FXML
    private ToggleGroup rbTipo;

    @FXML
    private GridPane rootPane;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtFInanciacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNumTrab;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtPais;
    private Blob imagenBlob; // Cambia InputStream a Blob

    /**
     * Constructor que inicializa el controlador con un objeto aeropuerto.
     *
     * @param aeropuerto Objeto aeropuerto.
     */
    public DatosAeropuertoController(Object aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Constructor vacío.
     */
    public DatosAeropuertoController() {}

    /**
     * Asigna el aeropuerto que se va a gestionar.
     *
     * @param aeropuerto Objeto aeropuerto.
     */
    public void setAeropuerto(Object aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Inicializa los componentes de la vista y carga los datos del aeropuerto.
     *
     * @param url URL de localización del recurso.
     * @param resourceBundle Paquete de recursos para la internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btGuardar.setDefaultButton(true);
        btCancelar.setCancelButton(true);
        rbTipo.selectedToggleProperty().addListener(this::cambioTipo);

        if (aeropuerto == null) {
            System.out.println("Null");
        } else {
            Aeropuerto airport;
            if (aeropuerto instanceof AeropuertoPublico) {
                AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                airport = aeropuertoPublico.getAeropuerto();
                txtFInanciacion.setText(aeropuertoPublico.getFinanciacion().toString());
                txtNumTrab.setText(aeropuertoPublico.getNum_trabajadores() + "");
            } else {
                AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                airport = aeropuertoPrivado.getAeropuerto();
                rbTipo.selectToggle(btPrivado);
                txtFInanciacion.setText(aeropuertoPrivado.getNumero_socios() + "");
                txtNumTrab.setText("");
            }
            this.ap = airport;
            btPublico.setDisable(true);
            btPrivado.setDisable(true);

            txtNombre.setText(airport.getNombre());
            txtPais.setText(airport.getDireccion().getPais());
            txtCiudad.setText(airport.getDireccion().getCiudad());
            txtCalle.setText(airport.getDireccion().getCalle());
            txtNumero.setText(airport.getDireccion().getNumero() + "");
            txtAnio.setText(airport.getAnio_inauguracion() + "");
            txtCapacidad.setText(airport.getCapacidad() + "");
            if (airport.getImagen() != null) {
                System.out.println("Has image");
                this.imagenBlob = airport.getImagen();
                InputStream imagen = null;
                try {
                    imagen = airport.getImagen().getBinaryStream();
                    imgView.setImage(new Image(imagen));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    /**
     * Método para gestionar el evento de guardar. Verifica los campos y guarda o modifica
     * el aeropuerto en la base de datos.
     *
     * @param event Evento de acción.
     */
    @FXML
    void guardar(ActionEvent event) {
        ArrayList<String> lst = new ArrayList<>();
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            lst.add("Campo nombre no puede estar vacío");
        }
        if (txtPais.getText().isEmpty()) {
            lst.add("Campo país no puede estar vacío");
        }
        if (txtCiudad.getText().isEmpty()) {
            lst.add("Campo ciudad no puede estar vacío");
        }
        if (txtCalle.getText().isEmpty()) {
            lst.add("Campo calle no puede estar vacío");
        }
        if (txtNumero.getText().isEmpty()) {
            lst.add("Campo número no puede estar vacío");
        } else {
            try {
                int numero = Integer.parseInt(txtNumero.getText());
            } catch (NumberFormatException e) {
                lst.add("Campo número tiene que ser numérico");
            }
        }
        if (txtAnio.getText().isEmpty()) {
            lst.add("Campo año de inauguración no puede estar vacío");
        } else {
            try {
                int anio_inauguracion = Integer.parseInt(txtAnio.getText());
            } catch (NumberFormatException e) {
                lst.add("Campo año de inauguración tiene que ser numérico");
            }
        }
        if (txtCapacidad.getText().isEmpty()) {
            lst.add("Campo capacidad no puede estar vacío");
        } else {
            try {
                int capacidad = Integer.parseInt(txtCapacidad.getText());
            } catch (NumberFormatException e) {
                lst.add("Campo capacidad tiene que ser numérico");
            }
        }
        if (btPublico.isSelected()) {
            if (txtFInanciacion.getText().isEmpty()) {
                lst.add("Campo financiación no puede estar vacío");
            } else if (!txtFInanciacion.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) {
                lst.add("Campo financiación tiene que ser decimal");
            }
            if (txtNumTrab.getText().isEmpty()) {
                lst.add("Campo número de trabajadores no puede estar vacío");
            } else {
                try {
                    int capacidad = Integer.parseInt(txtNumTrab.getText());
                } catch (NumberFormatException e) {
                    lst.add("Campo número de trabajadores tiene que ser numérico");
                }
            }
        } else {
            if (txtFInanciacion.getText().isEmpty()) {
                lst.add("Campo número de socios no puede estar vacío");
            } else {
                try {
                    int capacidad = Integer.parseInt(txtFInanciacion.getText());
                } catch (NumberFormatException e) {
                    lst.add("Campo número de socios tiene que ser numérico");
                }
            }
        }
        if (!error.isEmpty()) {
            lst.add(error);
            alerta(lst);
        } else {
            boolean resultado;
            if (this.aeropuerto == null) {
                resultado = crearAeropuerto();
            } else {
                resultado = modificarAeropuerto();
            }
            if (resultado) {
                Stage stage = (Stage) txtNombre.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Crea un aeropuerto en la base de datos.
     *
     * @return True si se creó exitosamente, false en caso contrario.
     */
    public boolean crearAeropuerto() {
        Direccion direccion = new Direccion();
        direccion.setPais(txtPais.getText());
        direccion.setCiudad(txtCiudad.getText());
        direccion.setCalle(txtCalle.getText());
        direccion.setNumero(Integer.parseInt(txtNumero.getText()));
        int id_direccion = direccionDao.insertar(direccion);
        if (id_direccion == -1) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("No se han podido cargar los datos.");
            alerta(lst);
            return false;
        } else {
            direccion.setId(id_direccion);
            Aeropuerto airport = new Aeropuerto();
            airport.setNombre(txtNombre.getText());
            airport.setDireccion(direccion);
            airport.setAnio_inauguracion(Integer.parseInt(txtAnio.getText()));
            airport.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            airport.setImagen(imagenBlob);
            int id_aeropuerto = aeropuertoDao.insertar(airport);
            if (id_aeropuerto == -1) {
                ArrayList<String> lst = new ArrayList<>();
                lst.add("No se han podido cargar los datos.");
                alerta(lst);
                return false;
            } else {
                airport.setId(id_aeropuerto);
                if (btPublico.isSelected()) {
                    AeropuertoPublico aeropuertoPublico = new AeropuertoPublico(airport, new BigDecimal(txtFInanciacion.getText()), Integer.parseInt(txtNumTrab.getText()));
                    if (!aeropuertoPublicoDao.insertar(aeropuertoPublico)) {
                        ArrayList<String> lst = new ArrayList<>();
                        lst.add("No se han podido cargar los datos.");
                        alerta(lst);
                        return false;
                    }
                } else {
                    AeropuertoPrivado aeropuertoPrivado = new AeropuertoPrivado(airport, Integer.parseInt(txtFInanciacion.getText()));
                    if (!aeropuertoPrivadoDao.insertar(aeropuertoPrivado)) {
                        ArrayList<String> lst = new ArrayList<>();
                        lst.add("No se han podido cargar los datos.");
                        alerta(lst);
                        return false;
                    }
                }
                ArrayList<String> lst = new ArrayList<>();
                lst.add("Aeropuerto creado correctamente");
                confirmacion(lst);
                return true;
            }
        }
    }

    /**
     * Modifica un aeropuerto en la base de datos.
     *
     * @return True si se actualizó exitosamente, false en caso contrario.
     */
    public boolean modificarAeropuerto() {
        Aeropuerto airport = new Aeropuerto();
        Direccion direccion = new Direccion();
        direccion.setId(ap.getDireccion().getId());
        direccion.setPais(txtPais.getText());
        direccion.setCiudad(txtCiudad.getText());
        direccion.setCalle(txtCalle.getText());
        direccion.setNumero(Integer.parseInt(txtNumero.getText()));
        if (!direccionDao.modificar(this.ap.getDireccion(), direccion)) {
            ArrayList<String> lst = new ArrayList<>();
            lst.add("No se han podido cargar los datos.");
            alerta(lst);
            return false;
        } else {
            airport.setDireccion(direccion);
            airport.setNombre(txtNombre.getText());
            airport.setAnio_inauguracion(Integer.parseInt(txtAnio.getText()));
            airport.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            airport.setImagen(imagenBlob);
            if (!aeropuertoDao.modificar(ap, airport)) {
                ArrayList<String> lst = new ArrayList<>();
                lst.add("No se han podido cargar los datos.");
                alerta(lst);
                return false;
            } else {
                if (this.aeropuerto instanceof AeropuertoPublico) {
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) this.aeropuerto;
                    AeropuertoPublico newAirport = new AeropuertoPublico(airport, new BigDecimal(txtFInanciacion.getText()), Integer.parseInt(txtNumTrab.getText()));
                    if (!aeropuertoPublicoDao.modificar(aeropuertoPublico, newAirport)) {
                        ArrayList<String> lst = new ArrayList<>();
                        lst.add("No se han podido cargar los datos.");
                        alerta(lst);
                        return false;
                    }
                } else {
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) this.aeropuerto;
                    AeropuertoPrivado newAirport = new AeropuertoPrivado(airport, Integer.parseInt(txtFInanciacion.getText()));
                    if (!aeropuertoPrivadoDao.modificar(aeropuertoPrivado, newAirport)) {
                        ArrayList<String> lst = new ArrayList<>();
                        lst.add("No se han podido cargar los datos.");
                        alerta(lst);
                        return false;
                    }
                }
                ArrayList<String> lst = new ArrayList<>();
                lst.add("Aeropuerto actualizado correctamente");
                confirmacion(lst);
                return true;
            }
        }
    }

    /**
     * Cambia los campos de datos según el tipo de aeropuerto seleccionado (público o privado).
     *
     * @param observableValue Valor observable.
     * @param oldBtn Botón anterior.
     * @param newBtn Botón nuevo.
     */
    public void cambioTipo(ObservableValue<? extends Toggle> observableValue, Toggle oldBtn, Toggle newBtn) {
        if (newBtn.equals(btPublico)) {
            lblFinanciacion.setText("Financiación:");
            lblNumTrab.setText("Número de trabajadores:");
            txtNumTrab.setVisible(true);
        } else {
            lblFinanciacion.setText("Número de socios:");
            lblNumTrab.setText(null);
            txtNumTrab.setVisible(false);
        }
    }

    /**
     * Cancela la operación y cierra la ventana.
     *
     * @param event Evento de acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta de error con los mensajes especificados.
     *
     * @param textos Lista de mensajes de error.
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
     * Muestra una confirmación de éxito con los mensajes especificados.
     *
     * @param textos Lista de mensajes de confirmación.
     */
    public void confirmacion(ArrayList<String> textos) {
        String contenido = String.join("\n", textos);
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    @FXML
    public void elegirImg(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Aeropuerto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null); // Cambia null por la ventana principal si es necesario
        if (file != null) {
            try {
                InputStream imagen = new FileInputStream(file);
                Blob blob = aeropuertoDao.convertFileToBlob(file);
                this.imagenBlob = blob;
                imgView.setImage(new Image(imagen));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
