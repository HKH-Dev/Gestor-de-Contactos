package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.Contacto;
import co.edu.uniquindio.model.ContactoPrincipal;
import co.edu.uniquindio.model.implementacion.Personal;
import co.edu.uniquindio.model.implementacion.Profesional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {

    private Contacto contactoSeleccionado;
    private ContactoPrincipal contactoPrincipal;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtNumeroTelefono;
    @FXML
    private DatePicker txtFechaNacimiento;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> txtCategoria;

    @FXML
    private TableView<Contacto> tablaContactos;
    @FXML
    private TableColumn<Contacto, String> colNombre;
    @FXML
    private TableColumn<Contacto, String> colApellido;
    @FXML
    private TableColumn<Contacto, String> colNumeroTelefono;
    @FXML
    private TableColumn<Contacto, String> colFechaNacimiento;
    @FXML
    private TableColumn<Contacto, String> colEmail;
    @FXML
    private TableColumn<Contacto, String> colCategoria;

    @FXML
    private StackPane mainContent;

    public InicioControlador() {
        this.contactoPrincipal = new ContactoPrincipal();
    }

    @FXML
    public void crearContacto(ActionEvent e) {
        try {
            // Validar campos
            if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                    txtNumeroTelefono.getText().isEmpty() || txtFechaNacimiento.getValue() == null ||
                    txtEmail.getText().isEmpty() || txtCategoria.getValue() == null) {
                mostrarAlerta("Todos los campos son obligatorios", AlertType.WARNING);
                return;
            }

            String categoria = txtCategoria.getValue();

            // Crear contacto según categoría
            Contacto contacto;
            if (categoria.equals("Profesional")) {
                // Valores de ejemplo para profesion y lugarTrabajo
                contacto = new Profesional(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtNumeroTelefono.getText(),
                        txtFechaNacimiento.getValue(),
                        txtEmail.getText(),
                        "Ingeniero", // Puedes modificar esto para que el usuario lo ingrese
                        "Nasa"       // Puedes modificar esto para que el usuario lo ingrese
                );
            } else if (categoria.equals("Personal")) {
                // Valor de ejemplo para relacion
                contacto = new Personal(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtNumeroTelefono.getText(),
                        txtFechaNacimiento.getValue(),
                        txtEmail.getText(),
                        "Amigo" // Puedes modificar esto para que el usuario lo ingrese
                );
            } else {
                // Contacto genérico
                contacto = new Contacto(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtNumeroTelefono.getText(),
                        txtFechaNacimiento.getValue(),
                        txtEmail.getText()
                ) {
                    @Override
                    public String mostrarDetallesEspecificos() {
                        return "Categoría: " + categoria;
                    }

                    @Override
                    public String getCategoria() {
                        return categoria;
                    }
                };
            }

            contactoPrincipal.crearContacto(contacto);

            mostrarAlerta("Contacto creado correctamente", AlertType.INFORMATION);

            // Actualizar la tabla con los nuevos contactos
            tablaContactos.setItems(FXCollections.observableArrayList(contactoPrincipal.listarContactos()));

            // Limpiar campos
            limpiarCampos();
        } catch (Exception ex) {
            mostrarAlerta("Error: " + ex.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void mostrarContactos(ActionEvent e) {
        contactoPrincipal.mostrarContactos(txtNumeroTelefono.getText());
    }

    @FXML
    public void eliminarContacto(ActionEvent e) {
        try {
            contactoPrincipal.eliminarContacto(txtNumeroTelefono.getText());
            mostrarAlerta("Contacto eliminado correctamente", AlertType.INFORMATION);

            // Refrescar tabla
            tablaContactos.setItems(FXCollections.observableArrayList(contactoPrincipal.listarContactos()));

            limpiarCampos();
        } catch (Exception ex) {
            mostrarAlerta("Error: " + ex.getMessage(), AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtNumeroTelefono.clear();
        txtFechaNacimiento.setValue(null);
        txtEmail.clear();
        txtCategoria.setValue(null);
    }

    private void mostrarAlerta(String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las columnas de la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colNumeroTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroTelefono()));
        colFechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaNacimiento().toString()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));

        // Agregar categorías al ComboBox
        txtCategoria.setItems(FXCollections.observableArrayList(contactoPrincipal.listarCategorias()));

        // Agregar contactos de ejemplo
        agregarContactosPorDefecto();

        // Poblar tabla con contactos existentes
        tablaContactos.setItems(FXCollections.observableArrayList(contactoPrincipal.listarContactos()));

        // Configurar selección de fila en la tabla
        tablaContactos.setOnMouseClicked(e -> {
            contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();
            if (contactoSeleccionado != null) {
                txtNombre.setText(contactoSeleccionado.getNombre());
                txtApellido.setText(contactoSeleccionado.getApellido());
                txtNumeroTelefono.setText(contactoSeleccionado.getNumeroTelefono());
                txtFechaNacimiento.setValue(contactoSeleccionado.getFechaNacimiento());
                txtEmail.setText(contactoSeleccionado.getEmail());
                txtCategoria.setValue(contactoSeleccionado.getCategoria());
            }
        });
    }

    private void agregarContactosPorDefecto() {
        // Agregar un contacto Profesional de ejemplo
        Profesional profesional = new Profesional(
                "Juan",
                "Pérez",
                "123",
                LocalDate.of(1990, 6, 25),
                "juan.perez@example.com",
                "Ingeniero",
                "Nasa"
        );
        contactoPrincipal.crearContacto(profesional);

        // Agregar un contacto Personal de ejemplo
        Personal personal = new Personal(
                "María",
                "López",
                "111",
                LocalDate.of(1992, 8, 15),
                "maria.lopez@example.com",
                "Hermana"
        );
        contactoPrincipal.crearContacto(personal);
    }
}
