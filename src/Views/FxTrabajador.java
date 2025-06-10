package Views;

import Controller.ControlTrabajador;
import Model.Trabajador;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FxTrabajador extends Application {

    private ControlTrabajador controlTrabajador;
    private TableView<Trabajador> tablaTrabajadores;

    @Override
    public void start(Stage primaryStage) {
        controlTrabajador = new ControlTrabajador();

        mostrarCRUDTrabajadores(null); 
    }

    private void mostrarCRUDTrabajadores(Trabajador trabajadorLogueado) {
        Stage crudStage = new Stage();
        crudStage.setTitle("Trabajadores");

        tablaTrabajadores = new TableView<>();
        configurarTabla();

        // Campos del formulario (igual que antes)
        TextField txtId = new TextField();
        txtId.setPromptText("ID");
        txtId.setDisable(true);

        ComboBox<String> cbTipoDoc = new ComboBox<>();
        cbTipoDoc.getItems().addAll("DNI", "CE", "PASAPORTE", "RUC");
        cbTipoDoc.setPromptText("Tipo Documento");

        TextField txtDocumento = new TextField();
        TextField txtNombre = new TextField();
        TextField txtApellido = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtEmail = new TextField();
        DatePicker dpFechaNac = new DatePicker();
        TextField txtUsuario = new TextField();
        PasswordField txtContraseña = new PasswordField();
        TextField txtSalario = new TextField();
        ComboBox<String> cbRol = new ComboBox<>();
        ComboBox<String> cbEstado = new ComboBox<>();

        // Prompts (igual que antes)
        txtDocumento.setPromptText("Número Documento");
        txtNombre.setPromptText("Nombre");
        txtApellido.setPromptText("Apellido");
        txtTelefono.setPromptText("Teléfono");
        txtEmail.setPromptText("Email");
        dpFechaNac.setPromptText("Fecha Nacimiento");
        txtUsuario.setPromptText("Usuario");
        txtContraseña.setPromptText("Contraseña");
        txtSalario.setPromptText("Salario");
        cbRol.getItems().addAll("Administrador", "Vendedor", "Mecánico", "Supervisor");
        cbRol.setPromptText("Rol");
        cbEstado.getItems().addAll("Activo", "Inactivo", "Vacaciones", "Suspendido");
        cbEstado.setPromptText("Estado");

        // Botones
        Button btnAgregar = new Button("Agregar");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnLimpiar = new Button("Limpiar");

        // Configurar acciones (igual que antes)
        btnAgregar.setOnAction(e -> agregarTrabajador(
                cbTipoDoc.getValue(),
                txtDocumento.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                dpFechaNac.getValue() != null ? java.sql.Date.valueOf(dpFechaNac.getValue()) : null,
                txtUsuario.getText(),
                txtContraseña.getText(),
                cbRol.getValue(),
                cbEstado.getValue(),
                txtSalario.getText()
        ));

        btnEditar.setOnAction(e -> editarTrabajador(
                txtId.getText(),
                cbTipoDoc.getValue(),
                txtDocumento.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                dpFechaNac.getValue() != null ? java.sql.Date.valueOf(dpFechaNac.getValue()) : null,
                txtUsuario.getText(),
                txtContraseña.getText(),
                cbRol.getValue(),
                cbEstado.getValue(),
                txtSalario.getText()
        ));

        btnEliminar.setOnAction(e -> eliminarTrabajador(txtId.getText()));

        btnLimpiar.setOnAction(e -> limpiarFormulario(
                txtId, cbTipoDoc, txtDocumento, txtNombre, txtApellido,
                txtTelefono, txtEmail, dpFechaNac, txtUsuario,
                txtContraseña, cbRol, cbEstado, txtSalario
        ));

        // Listener selección tabla (igual)
        tablaTrabajadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosFormulario(
                        newSel, txtId, cbTipoDoc, txtDocumento, txtNombre,
                        txtApellido, txtTelefono, txtEmail, dpFechaNac,
                        txtUsuario, txtContraseña, cbRol, cbEstado, txtSalario
                );
            }
        });

        // FORMULARIO 3x3
        GridPane formulario = new GridPane();
        formulario.setPadding(new Insets(15));
        formulario.setHgap(15);
        formulario.setVgap(15);

        // Coloca labels y controles en 3 columnas x 6 filas aprox.
        // Fila 0
        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(txtId, 1, 0);
        formulario.add(new Label("Tipo Documento:"), 2, 0);
        formulario.add(cbTipoDoc, 3, 0);
        formulario.add(new Label("Número Documento:"), 4, 0);
        formulario.add(txtDocumento, 5, 0);

        // Fila 1
        formulario.add(new Label("Nombre:"), 0, 1);
        formulario.add(txtNombre, 1, 1);
        formulario.add(new Label("Apellido:"), 2, 1);
        formulario.add(txtApellido, 3, 1);
        formulario.add(new Label("Teléfono:"), 4, 1);
        formulario.add(txtTelefono, 5, 1);

        // Fila 2
        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(txtEmail, 1, 2);
        formulario.add(new Label("Fecha Nacimiento:"), 2, 2);
        formulario.add(dpFechaNac, 3, 2);
        formulario.add(new Label("Usuario:"), 4, 2);
        formulario.add(txtUsuario, 5, 2);

        // Fila 3
        formulario.add(new Label("Contraseña:"), 0, 3);
        formulario.add(txtContraseña, 1, 3);
        formulario.add(new Label("Rol:"), 2, 3);
        formulario.add(cbRol, 3, 3);
        formulario.add(new Label("Estado:"), 4, 3);
        formulario.add(cbEstado, 5, 3);

        // Fila 4
        formulario.add(new Label("Salario:"), 0, 4);
        formulario.add(txtSalario, 1, 4);

        // Botones en HBox centrados
        HBox botones = new HBox(20, btnAgregar, btnEditar, btnEliminar, btnLimpiar);
        botones.setPadding(new Insets(15));
        botones.setStyle("-fx-alignment: center;");

        // Layout principal: VBox con formulario, botones y tabla abajo
        VBox layout = new VBox(10, formulario, botones, tablaTrabajadores);
        layout.setPadding(new Insets(20));
        VBox.setVgrow(tablaTrabajadores, Priority.ALWAYS);

        actualizarTabla();

        Scene scene = new Scene(layout, 1000, 700);
        crudStage.setScene(scene);
        crudStage.show();
    }

    private void configurarTabla() {
        TableColumn<Trabajador, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idtrabajador"));

        TableColumn<Trabajador, String> colTipoDoc = new TableColumn<>("Tipo Doc");
        colTipoDoc.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));

        TableColumn<Trabajador, String> colDocumento = new TableColumn<>("Documento");
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("numeroDocumento"));

        TableColumn<Trabajador, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Trabajador, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        TableColumn<Trabajador, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Trabajador, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        TableColumn<Trabajador, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<Trabajador, Double> colSalario = new TableColumn<>("Salario");
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

        tablaTrabajadores.getColumns().addAll(colId, colTipoDoc, colDocumento, colNombre,
                colApellido, colUsuario, colRol, colEstado, colSalario);
    }

    private void actualizarTabla() {
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList(controlTrabajador.listarTrabajadores());
        tablaTrabajadores.setItems(trabajadores);
    }

    private void agregarTrabajador(String tipoDoc, String documento, String nombre, String apellido,
            String telefono, String email, Date fechaNac, String usuario,
            String contraseña, String rol, String estado, String salario) {
        try {
            if (tipoDoc == null || documento.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                    || telefono.isEmpty() || email.isEmpty() || fechaNac == null || usuario.isEmpty()
                    || contraseña.isEmpty() || rol == null || estado == null || salario.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            Trabajador nuevo = new Trabajador(
                    0, // idpersona (se generará automáticamente)
                    tipoDoc, documento, nombre, apellido, telefono, email, (java.sql.Date) fechaNac,
                    0, // idtrabajador (se generará automáticamente)
                    rol, usuario, contraseña, estado, Double.parseDouble(salario)
            );

            int resultado = controlTrabajador.registrarTrabajador(nuevo);
            if (resultado > 0) {
                mostrarAlerta("Éxito", "Trabajador agregado correctamente con ID: " + resultado, Alert.AlertType.INFORMATION);
                actualizarTabla();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el trabajador", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El salario debe ser un número válido", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar trabajador: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void editarTrabajador(String idStr, String tipoDoc, String documento, String nombre, String apellido,
            String telefono, String email, Date fechaNac, String usuario,
            String contraseña, String rol, String estado, String salario) {
        try {
            int id = Integer.parseInt(idStr);
            Trabajador existente = controlTrabajador.buscarTrabajador(id);

            if (existente != null) {
                existente.setTipoDocumento(tipoDoc);
                existente.setNumeroDocumento(documento);
                existente.setNombre(nombre);
                existente.setApellido(apellido);
                existente.setTelefono(telefono);
                existente.setEmail(email);
                existente.setFechaNacimiento((java.sql.Date) fechaNac);
                existente.setUsuario(usuario);
                existente.setContraseña(contraseña);
                existente.setRol(rol);
                existente.setEstado(estado);
                existente.setSalario(Double.parseDouble(salario));

                if (controlTrabajador.actualizarTrabajador(existente)) {
                    mostrarAlerta("Éxito", "Trabajador actualizado correctamente", Alert.AlertType.INFORMATION);
                    actualizarTabla();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el trabajador", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "No se encontró el trabajador", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID o salario inválido", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar trabajador: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void eliminarTrabajador(String idStr) {
        try {
            int id = Integer.parseInt(idStr);

            if (controlTrabajador.eliminarTrabajador(id)) {
                mostrarAlerta("Éxito", "Trabajador eliminado correctamente", Alert.AlertType.INFORMATION);
                actualizarTabla();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el trabajador", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID inválido", Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosFormulario(Trabajador trabajador, TextField txtId, ComboBox<String> cbTipoDoc,
            TextField txtDocumento, TextField txtNombre, TextField txtApellido,
            TextField txtTelefono, TextField txtEmail, DatePicker dpFechaNac,
            TextField txtUsuario, PasswordField txtContraseña, ComboBox<String> cbRol,
            ComboBox<String> cbEstado, TextField txtSalario) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        txtId.setText(String.valueOf(trabajador.getIdtrabajador()));
        cbTipoDoc.setValue(trabajador.getTipoDocumento());
        txtDocumento.setText(trabajador.getNumeroDocumento());
        txtNombre.setText(trabajador.getNombre());
        txtApellido.setText(trabajador.getApellido());
        txtTelefono.setText(trabajador.getTelefono());
        txtEmail.setText(trabajador.getEmail());
        if (trabajador.getFechaNacimiento() != null) {
            dpFechaNac.setValue(trabajador.getFechaNacimiento().toLocalDate());
        }
        txtUsuario.setText(trabajador.getUsuario());
        txtContraseña.setText(trabajador.getContraseña());
        cbRol.setValue(trabajador.getRol());
        cbEstado.setValue(trabajador.getEstado());
        txtSalario.setText(String.valueOf(trabajador.getSalario()));
    }

    private void limpiarFormulario(TextField txtId, ComboBox<String> cbTipoDoc, TextField txtDocumento,
            TextField txtNombre, TextField txtApellido, TextField txtTelefono,
            TextField txtEmail, DatePicker dpFechaNac, TextField txtUsuario,
            PasswordField txtContraseña, ComboBox<String> cbRol, ComboBox<String> cbEstado,
            TextField txtSalario) {
        txtId.clear();
        cbTipoDoc.getSelectionModel().clearSelection();
        txtDocumento.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEmail.clear();
        dpFechaNac.setValue(null);
        txtUsuario.clear();
        txtContraseña.clear();
        cbRol.getSelectionModel().clearSelection();
        cbEstado.getSelectionModel().clearSelection();
        txtSalario.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
