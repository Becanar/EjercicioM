package com.example.ejerciciom.controladores;

import com.example.ejerciciom.dao.aeropuertoDao;
import com.example.ejerciciom.dao.aeropuertoPrivadoDao;
import com.example.ejerciciom.dao.aeropuertoPublicoDao;
import com.example.ejerciciom.dao.avionDao;
import com.example.ejerciciom.model.Aeropuerto;
import com.example.ejerciciom.model.AeropuertoPrivado;
import com.example.ejerciciom.model.AeropuertoPublico;
import com.example.ejerciciom.model.Avion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
/**
 * Controlador para la gestión de aeropuertos y aviones en la interfaz de usuario.
 * Permite la visualización de información, adición y eliminación de aviones, y la carga de listas
 * de aeropuertos públicos y privados en la tabla.
 */
public class aeropuertosControler implements Initializable{

    @FXML
    private MenuItem actDesAvion, aniadirAeropuerto, aniadirAvion, borrarAeropuerto, borrarAvion, editarAeropuerto, infoAeropuerto;
    @FXML
    private Menu menuAeropuertos, menuAviones, menuAyuda;
    @FXML
    private RadioButton btPrivados, btPublicos;
    @FXML
    private ToggleGroup rbGroup;
    @FXML
    private Label lblListado, lblNombre;
    @FXML
    private MenuBar barraMenu;
    @FXML
    private TextField txtNombre;
    @FXML
    private HBox panelBotones, panelHueco;
    @FXML
    private TilePane panelCentro;
    @FXML
    private FlowPane panelListado;
    @FXML
    private VBox rootPane;
    @FXML
    private TableView tablaVista;

    private ObservableList lstEntera = FXCollections.observableArrayList();
    private ObservableList lstFiltrada = FXCollections.observableArrayList();

    /**
     * Método de inicialización de JavaFX. Configura los listeners para manejar la selección de elementos,
     * carga de aeropuertos públicos y privados, y la funcionalidad de filtrado.
     * @param url Ubicación usada para resolver rutas relativas para el objeto raíz, o null si no se proporciona.
     * @param resourceBundle Recurso local usado para localizar el objeto raíz, o null si no se proporciona.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaVista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
                if (newValue != null) {
                    deshabilitarMenus(false);
                } else {
                    deshabilitarMenus(true);
                }
            }
        });
        cargarPublicos();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Editar Aeropuerto");
        editItem.setOnAction(event -> editarAeropuerto(null));


        MenuItem deleteItem = new MenuItem("Borrar Aeropuerto");
        deleteItem.setOnAction(event -> borrarAeropuerto(null));

        contextMenu.getItems().addAll(editItem, deleteItem);

        tablaVista.setContextMenu(contextMenu);
        tablaVista.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                infoAeropuerto(null);
            }
        });
        rbGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldBtn, Toggle newBtn) -> {
            if (newBtn.equals(btPublicos)) {
                cargarPublicos();
            } else {
                cargarPrivados();
            }
        });
        rootPane.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.F) {
                txtNombre.requestFocus();
                event.consume();
            }
        });
        txtNombre.setOnKeyTyped(keyEvent -> filtrar());
    }
    /**
     * Método para añadir un nuevo aeropuerto mediante una nueva ventana de entrada de datos.
     * @param event Evento de acción asociado al botón de añadir aeropuerto.
     */
    @FXML
    void aniadirAeropuerto(ActionEvent event) {
        try {
            Window ventana = btPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ejerciciom/fxml/DatosAeropuerto.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            try {
                Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
                stage.getIcons().add(img);
            } catch (Exception e) {
                ArrayList<String> lst=new ArrayList<>();
                lst.add("No se ha podido cargar la imagen.");
                alerta(lst);
            }
            scene.getStylesheets().add(getClass().getResource("/com/example/ejerciciom/estilo/style.css").toExternalForm());
            stage.setTitle("AVIONES - AÑADIR AEROPUERTO");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (btPublicos.isSelected()) {
                cargarPublicos();
            } else {
                cargarPrivados();
            }
        } catch (IOException e) {
            ArrayList<String> lst=new ArrayList<>();
            lst.add("No se ha podido abrir la ventana.");
            alerta(lst);
        }
    }

    /**
     * Método para editar la información de un aeropuerto seleccionado. Si no hay aeropuerto
     * seleccionado, se muestra una alerta.
     * @param event Evento de acción asociado al botón de editar aeropuerto.
     */
    @FXML
    void editarAeropuerto(ActionEvent event) {
        Object aeropuerto = tablaVista.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            ArrayList<String> lst=new ArrayList<>();
            lst.add("No has seleccionado ningún aeropuerto.");
            alerta(lst);
        } else {
            try {
                Window ventana = btPrivados.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ejerciciom/fxml/DatosAeropuerto.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                try {
                    Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
                    stage.getIcons().add(img);
                } catch (Exception e) {
                    ArrayList<String> lst=new ArrayList<>();
                    lst.add("No se ha podido cargar la imagen.");
                    alerta(lst);
                }
                scene.getStylesheets().add(getClass().getResource("/com/example/ejerciciom/estilo/style.css").toExternalForm());
                stage.setTitle("AVIONES - EDITAR AEROPUERTO");
                stage.initOwner(ventana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (btPublicos.isSelected()) {
                    cargarPublicos();
                } else {
                    cargarPrivados();
                }
            } catch (IOException e) {
                ArrayList<String> lst=new ArrayList<>();
                lst.add("No se ha podido abrir la ventana.");
                alerta(lst);
            }
        }
    }
    /**
     * Método para borrar un aeropuerto seleccionado. Confirma la acción con el usuario,
     * y si el usuario confirma, procede a eliminar el aeropuerto y sus aviones asociados.
     * @param event Evento de acción asociado al botón de borrar aeropuerto.
     */
    @FXML
    void borrarAeropuerto(ActionEvent event) {
        Object aeropuerto = tablaVista.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            ArrayList<String> lst=new ArrayList<>();
            lst.add("No has seleccionado ningún aeropuerto.");
            alerta(lst);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(tablaVista.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estás seguro que quieres eliminar ese aeropuerto? Esto también eliminara los aviones en este aeropuerto.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (aeropuerto instanceof AeropuertoPublico) {
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                    ObservableList<Avion> aviones = avionDao.cargarListado(aeropuertoPublico.getAeropuerto());
                    for (Avion avion : aviones) {
                        if (!avionDao.eliminar(avion)) {
                            ArrayList<String> lst=new ArrayList<>();
                            lst.add("No se ha podido eliminar el avión.");
                            alerta(lst);
                            return;
                        }
                    }
                    Aeropuerto airport = aeropuertoPublico.getAeropuerto();
                    if (aeropuertoPublicoDao.eliminar(aeropuertoPublico)) {
                        if (aeropuertoDao.eliminar(airport)) {
                            cargarPublicos();
                            confirmacion("Aeropuerto eliminado correctamente");
                        } else {
                            ArrayList<String> lst=new ArrayList<>();
                            lst.add("No se ha podido eliminar el aeropuerto.");
                            alerta(lst);
                        }
                    } else {
                        ArrayList<String> lst=new ArrayList<>();
                        lst.add("No se ha podido eliminar el aeropuerto.");
                        alerta(lst);
                    }
                } else {
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                    ObservableList<Avion> aviones = avionDao.cargarListado(aeropuertoPrivado.getAeropuerto());
                    for (Avion avion : aviones) {
                        if (!avionDao.eliminar(avion)) {
                            ArrayList<String> lst=new ArrayList<>();
                            lst.add("No se ha podido eliminar el avion.");
                            alerta(lst);
                            return;
                        }
                    }
                    Aeropuerto airport = aeropuertoPrivado.getAeropuerto();
                    if (aeropuertoPrivadoDao.eliminar(aeropuertoPrivado)) {
                        if (aeropuertoDao.eliminar(airport)) {
                            cargarPrivados();
                            confirmacion("Aeropuerto eliminado correctamente");
                        } else {
                            ArrayList<String> lst=new ArrayList<>();
                            lst.add("No se ha podido eliminar el aeropuerto.");
                            alerta(lst);
                        }
                    } else {
                        ArrayList<String> lst=new ArrayList<>();
                        lst.add("No se ha podido eliminar el aeropuerto.");
                        alerta(lst);
                    }
                }
            }
        }
    }

    /**
     * Muestra información del aeropuerto seleccionado en la tabla.
     * Si no hay un aeropuerto seleccionado, muestra un mensaje de error.
     *
     * @param event Evento de acción que dispara la visualización de la información del aeropuerto.
     */
    @FXML
    void infoAeropuerto(ActionEvent event) {
        Object aeropuerto = tablaVista.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            ArrayList<String> errores = new ArrayList<>();
            errores.add("Selecciona un aeropuerto antes de ver su información");
            alerta(errores);
        } else {
            ArrayList<String> info = new ArrayList<>();
            if (aeropuerto instanceof AeropuertoPublico) {
                AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                Aeropuerto airport = aeropuertoPublico.getAeropuerto();

                info.add("Nombre: " + airport.getNombre());
                info.add("País: " + airport.getDireccion().getPais());
                info.add("Dirección: C\\ " + airport.getDireccion().getCalle() + " " + airport.getDireccion().getNumero() + ", " + airport.getDireccion().getCiudad());
                info.add("Año de inauguración: " + airport.getAnio_inauguracion());
                info.add("Capacidad: " + airport.getCapacidad());
                info.add("Aviones:");

                ObservableList<Avion> aviones = avionDao.cargarListado(airport);
                for (Avion avion : aviones) {
                    info.add("\tModelo: " + avion.getModelo());
                    info.add("\tNúmero de asientos: " + avion.getNumero_asientos());
                    info.add("\tVelocidad máxima: " + avion.getVelocidad_maxima());
                    info.add("\t" + (avion.isActivado() ? "Activado" : "Desactivado"));
                }

                info.add("Público");
                info.add("Financiación: " + aeropuertoPublico.getFinanciacion());
                info.add("Número de trabajadores: " + aeropuertoPublico.getNum_trabajadores());

            } else if (aeropuerto instanceof AeropuertoPrivado) {
                AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                Aeropuerto airport = aeropuertoPrivado.getAeropuerto();

                info.add("Nombre: " + airport.getNombre());
                info.add("País: " + airport.getDireccion().getPais());
                info.add("Dirección: C\\ " + airport.getDireccion().getCalle() + " " + airport.getDireccion().getNumero() + ", " + airport.getDireccion().getCiudad());
                info.add("Año de inauguración: " + airport.getAnio_inauguracion());
                info.add("Capacidad: " + airport.getCapacidad());
                info.add("Aviones:");

                ObservableList<Avion> aviones = avionDao.cargarListado(airport);
                for (Avion avion : aviones) {
                    info.add("\tModelo: " + avion.getModelo());
                    info.add("\tNúmero de asientos: " + avion.getNumero_asientos());
                    info.add("\tVelocidad máxima: " + avion.getVelocidad_maxima());
                    info.add("\t" + (avion.isActivado() ? "Activado" : "Desactivado"));
                }

                info.add("Privado");
                info.add("Número de socios: " + aeropuertoPrivado.getNumero_socios());
            }

            // Convertimos el ArrayList a un solo String, con saltos de línea
            String contenido = String.join("\n", info);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Información");
            alerta.setContentText(contenido);
            alerta.showAndWait();
        }
    }
    /**
     * Abre una ventana modal para añadir un avión a la base de datos de aviones.
     * En caso de error en la carga de la ventana o imagen, muestra un mensaje de error.
     *
     * @param event Evento de acción que dispara la apertura de la ventana para añadir avión.
     */
    @FXML
    void aniadirAvion(ActionEvent event) {
        try {
            Window ventana = btPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ejerciciom/fxml/AniadirAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            try {
                Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
                stage.getIcons().add(img);
            } catch (Exception e) {
                ArrayList<String> lst=new ArrayList<>();
                lst.add("No se ha podido cargar la imagen.");
                alerta(lst);
            }
            scene.getStylesheets().add(getClass().getResource("/com/example/ejerciciom/estilo/style.css").toExternalForm());
            stage.setTitle("AVIONES - AÑADIR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            ArrayList<String> lst=new ArrayList<>();
            lst.add("No se ha podido abrir la ventana.");
            alerta(lst);
        }
    }
    /**
     * Abre una ventana modal para activar o desactivar el estado de un avión.
     * En caso de error en la carga de la ventana o imagen, muestra un mensaje de error.
     *
     * @param event Evento de acción que dispara la apertura de la ventana para activar/desactivar avión.
     */
    @FXML
    void activarDesactivarAvion(ActionEvent event) {
        try {
            Window ventana = btPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ejerciciom/fxml/ActivarDesactivarAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            try {
                Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
                stage.getIcons().add(img);
            } catch (Exception e) {
                ArrayList<String> lst=new ArrayList<>();
                lst.add("No se ha podido cargar la imagen.");
                alerta(lst);
            }
            scene.getStylesheets().add(getClass().getResource("/com/example/ejerciciom/estilo/style.css").toExternalForm());
            stage.setTitle("AVIONES - ACTIVAR/DESACTIVAR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            ArrayList<String> lst=new ArrayList<>();
            lst.add("No se ha podido abrir la ventana.");
            alerta(lst);
        }
    }
    /**
     * Abre una ventana modal para eliminar un avión de la base de datos.
     * En caso de error en la carga de la ventana o imagen, muestra un mensaje de error.
     *
     * @param event Evento de acción que dispara la apertura de la ventana para borrar avión.
     */
    @FXML
    void borrarAvion(ActionEvent event) {
        try {
            Window ventana = btPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ejerciciom/fxml/BorrarAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            try {
                Image img = new Image(getClass().getResource("/com/example/ejerciciom/images/plane.png").toString());
                stage.getIcons().add(img);
            } catch (Exception e) {
                ArrayList<String> lst=new ArrayList<>();
                lst.add("No se ha podido cargar la imagen.");
                alerta(lst);
            }
            scene.getStylesheets().add(getClass().getResource("/com/example/ejerciciom/estilo/style.css").toExternalForm());
            stage.setTitle("AVIONES - BORRAR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            ArrayList<String> lst=new ArrayList<>();
            lst.add("No se ha podido abrir la ventana.");
            alerta(lst);
        }
    }

    /**
     * Carga la lista de aeropuertos públicos en la tabla de la interfaz.
     * Configura las columnas de la tabla con la información de cada aeropuerto público.
     * Muestra un mensaje de error si no se encuentran aeropuertos o en caso de excepciones.
     */
    public void cargarPublicos() {
        try {
            tablaVista.getSelectionModel().clearSelection();
            txtNombre.setText(null);
            lstEntera.clear();
            lstFiltrada.clear();
            tablaVista.getItems().clear();
            tablaVista.getColumns().clear();

            TableColumn<AeropuertoPublico, Integer> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getId()));

            TableColumn<AeropuertoPublico, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getNombre()));

            TableColumn<AeropuertoPublico, String> colPais = new TableColumn<>("País");
            colPais.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getPais()));

            TableColumn<AeropuertoPublico, String> colCiudad = new TableColumn<>("Ciudad");
            colCiudad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCiudad()));

            TableColumn<AeropuertoPublico, String> colCalle = new TableColumn<>("Calle");
            colCalle.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCalle()));

            TableColumn<AeropuertoPublico, Integer> colNumero = new TableColumn<>("Número");
            colNumero.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getNumero()));

            TableColumn<AeropuertoPublico, Integer> colAnio = new TableColumn<>("Año");
            colAnio.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getAnio_inauguracion()));

            TableColumn<AeropuertoPublico, Integer> colCapacidad = new TableColumn<>("Capacidad");
            colCapacidad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getCapacidad()));

            TableColumn<AeropuertoPublico, BigDecimal> colFinanciacion = new TableColumn<>("Financiación");
            colFinanciacion.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getFinanciacion()));

            TableColumn<AeropuertoPublico, Integer> colTrabajadores = new TableColumn<>("Nº Trabajadores");
            colTrabajadores.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getNum_trabajadores()));

            tablaVista.getColumns().addAll(colId, colNombre, colPais, colCiudad, colCalle, colNumero, colAnio, colCapacidad, colFinanciacion, colTrabajadores);
            ObservableList<AeropuertoPublico> aeropuertos = aeropuertoPublicoDao.cargarListado();

            if (aeropuertos != null && !aeropuertos.isEmpty()) {
                lstEntera.setAll(aeropuertos);
                tablaVista.setItems(aeropuertos);
            } else {
                ArrayList<String> lst=new ArrayList<>();
                lst.add("No se encontraron Aeropuertos.");
                alerta(lst);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    /**
     * Carga la lista de aeropuertos privados en la tabla de la interfaz.
     * Configura las columnas de la tabla con la información de cada aeropuerto privado.
     */
    public void cargarPrivados() {

        tablaVista.getSelectionModel().clearSelection();
        txtNombre.setText(null);
        lstEntera.clear();
        lstFiltrada.clear();
        tablaVista.getItems().clear();
        tablaVista.getColumns().clear();

        TableColumn<AeropuertoPrivado, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getId()));
        TableColumn<AeropuertoPrivado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getNombre()));
        TableColumn<AeropuertoPrivado, String> colPais = new TableColumn<>("País");
        colPais.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getPais()));
        TableColumn<AeropuertoPrivado, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCiudad()));
        TableColumn<AeropuertoPrivado, String> colCalle = new TableColumn<>("Calle");
        colCalle.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCalle()));
        TableColumn<AeropuertoPrivado, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getNumero()));
        TableColumn<AeropuertoPrivado, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getAnio_inauguracion()));
        TableColumn<AeropuertoPrivado, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getCapacidad()));
        TableColumn<AeropuertoPrivado, Integer> colSocios = new TableColumn<>("Nº Socios");
        colSocios.setCellValueFactory(new PropertyValueFactory("numero_socios"));
        tablaVista.getColumns().addAll(colId, colNombre, colPais, colCiudad, colCalle, colNumero, colAnio, colCapacidad, colSocios);
        ObservableList<AeropuertoPrivado> aeropuertos = aeropuertoPrivadoDao.cargarListado();
        lstEntera.setAll(aeropuertos);
        tablaVista.setItems(aeropuertos);
    }
    /**
     * Habilita o deshabilita las opciones de edición, eliminación e información del menú de aeropuertos.
     *
     * @param deshabilitado booleano que indica si las opciones deben estar habilitadas o deshabilitadas.
     */
    public void deshabilitarMenus(boolean deshabilitado) {
        editarAeropuerto.setDisable(deshabilitado);
        borrarAeropuerto.setDisable(deshabilitado);
        infoAeropuerto.setDisable(deshabilitado);
    }

    /**
     * Filtra la lista de aeropuertos en función del nombre ingresado en el campo de texto.
     * Aplica el filtro sobre la lista de aeropuertos completa y muestra los resultados en la tabla.
     */
    public void filtrar() {
        String valor = txtNombre.getText();
        if (valor==null) {
            tablaVista.setItems(lstEntera);
        } else {
            valor = valor.toLowerCase();
            lstFiltrada.clear();
            if (lstEntera.getFirst() instanceof AeropuertoPublico) {
                for (Object aeropuerto : lstEntera) {
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                    String nombre = aeropuertoPublico.getAeropuerto().getNombre();
                    nombre = nombre.toLowerCase();
                    if (nombre.contains(valor)) {
                        lstFiltrada.add(aeropuertoPublico);
                    }
                }
            } else {
                for (Object aeropuerto : lstEntera) {
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                    String nombre = aeropuertoPrivado.getAeropuerto().getNombre();
                    nombre = nombre.toLowerCase();
                    if (nombre.contains(valor)) {
                        lstFiltrada.add(aeropuertoPrivado);
                    }
                }
            }
            tablaVista.setItems(lstFiltrada);
        }
    }

    /**
     * Muestra una alerta de error con los mensajes proporcionados.
     *
     * @param textos Lista de mensajes a mostrar en la alerta de error.
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
     * Muestra una alerta de confirmación con el mensaje proporcionado.
     *
     * @param texto Mensaje a mostrar en la alerta de confirmación.
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }


}
