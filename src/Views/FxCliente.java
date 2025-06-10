package Views;

import Controller.ControlCliente;
import Model.Cliente;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FxCliente extends Application {

    private ControlCliente controlCliente;
    private TableView<Cliente> tablaClientes;

    @Override
    public void start(Stage primaryStage) {
        controlCliente = new ControlCliente();
        mostrarCRUDClientes(primaryStage);
    }

    private void mostrarCRUDClientes(Stage primaryStage) {
        primaryStage.setTitle("Clientes");

        tablaClientes = new TableView<>();
        configurarTabla();

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
        DatePicker dpFechaRegistro = new DatePicker();

        txtDocumento.setPromptText("Número Documento");
        txtNombre.setPromptText("Nombre");
        txtApellido.setPromptText("Apellido");
        txtTelefono.setPromptText("Teléfono");
        txtEmail.setPromptText("Email");
        dpFechaNac.setPromptText("Fecha Nacimiento");
        dpFechaRegistro.setPromptText("Fecha Registro");

        Button btnAgregar = new Button("Agregar");
        Button btnEditar = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnLimpiar = new Button("Limpiar");
        Button btnExportar = new Button("Exportar Excel");
        btnExportar.setOnAction(e -> exportarClientesAExcel(tablaClientes.getItems()));

        btnAgregar.setOnAction(e -> agregarCliente(
                cbTipoDoc.getValue(),
                txtDocumento.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                dpFechaNac.getValue() != null ? Date.valueOf(dpFechaNac.getValue()) : null,
                dpFechaRegistro.getValue() != null ? Date.valueOf(dpFechaRegistro.getValue()) : null
        ));

        btnEditar.setOnAction(e -> editarCliente(
                txtId.getText(),
                cbTipoDoc.getValue(),
                txtDocumento.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                dpFechaNac.getValue() != null ? Date.valueOf(dpFechaNac.getValue()) : null,
                dpFechaRegistro.getValue() != null ? Date.valueOf(dpFechaRegistro.getValue()) : null
        ));

        btnEliminar.setOnAction(e -> eliminarCliente(txtId.getText()));

        btnLimpiar.setOnAction(e -> limpiarFormulario(
                txtId, cbTipoDoc, txtDocumento, txtNombre, txtApellido,
                txtTelefono, txtEmail, dpFechaNac, dpFechaRegistro
        ));

        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosFormulario(
                        newSel, txtId, cbTipoDoc, txtDocumento, txtNombre,
                        txtApellido, txtTelefono, txtEmail, dpFechaNac,
                        dpFechaRegistro
                );
            }
        });

        GridPane formulario = new GridPane();
        formulario.setPadding(new Insets(20));
        formulario.setHgap(10);
        formulario.setVgap(10);

        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(txtId, 1, 0);

        formulario.add(new Label("Tipo Documento:"), 2, 0);
        formulario.add(cbTipoDoc, 3, 0);

        formulario.add(new Label("Número Documento:"), 4, 0);
        formulario.add(txtDocumento, 5, 0);

        formulario.add(new Label("Nombre:"), 0, 1);
        formulario.add(txtNombre, 1, 1);

        formulario.add(new Label("Apellido:"), 2, 1);
        formulario.add(txtApellido, 3, 1);

        formulario.add(new Label("Teléfono:"), 4, 1);
        formulario.add(txtTelefono, 5, 1);

        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(txtEmail, 1, 2);

        formulario.add(new Label("Fecha Nacimiento:"), 2, 2);
        formulario.add(dpFechaNac, 3, 2);

        formulario.add(new Label("Fecha Registro:"), 4, 2);
        formulario.add(dpFechaRegistro, 5, 2);

        for (javafx.scene.Node node : formulario.getChildren()) {
            if (node instanceof TextField tf) {
                tf.setPrefWidth(150);
            }
            if (node instanceof ComboBox cb) {
                cb.setPrefWidth(150);
            }
            if (node instanceof DatePicker dp) {
                dp.setPrefWidth(150);
            }
        }

        HBox botones = new HBox(15, btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnExportar);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(15, 0, 15, 0));

        ScrollPane tablaScroll = new ScrollPane(tablaClientes);
        tablaScroll.setFitToWidth(true);
        tablaScroll.setFitToHeight(true);

        VBox layout = new VBox(20, formulario, botones, tablaScroll);
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(1100);
        layout.setPrefHeight(700);

        actualizarTabla();

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarTabla() {
        tablaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idcliente"));

        TableColumn<Cliente, String> colTipoDoc = new TableColumn<>("Tipo Doc");
        colTipoDoc.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));

        TableColumn<Cliente, String> colDocumento = new TableColumn<>("Documento");
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("numeroDocumento"));

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Cliente, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Cliente, Date> colFechaNac = new TableColumn<>("Fecha Nac.");
        colFechaNac.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        TableColumn<Cliente, Date> colFechaReg = new TableColumn<>("Fecha Reg.");
        colFechaReg.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));

        tablaClientes.getColumns().addAll(
                colId, colTipoDoc, colDocumento, colNombre,
                colApellido, colTelefono, colEmail, colFechaNac, colFechaReg
        );
    }

    private void actualizarTabla() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(controlCliente.listarClientes());
        tablaClientes.setItems(clientes);
    }

    private void agregarCliente(String tipoDoc, String documento, String nombre, String apellido,
            String telefono, String email, Date fechaNac, Date fechaRegistro) {
        try {
            if (tipoDoc == null || documento.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                    || telefono.isEmpty() || email.isEmpty() || fechaNac == null || fechaRegistro == null) {
                mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            Cliente nuevo = new Cliente(0, tipoDoc, documento, nombre, apellido, telefono, email, fechaNac, 0, fechaRegistro);
            int resultado = controlCliente.registrarCliente(nuevo);
            if (resultado > 0) {
                mostrarAlerta("Éxito", "Cliente agregado correctamente con ID: " + resultado, Alert.AlertType.INFORMATION);
                actualizarTabla();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el cliente", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void editarCliente(String idStr, String tipoDoc, String documento, String nombre, String apellido,
            String telefono, String email, Date fechaNac, Date fechaRegistro) {
        try {
            int id = Integer.parseInt(idStr);
            Cliente existente = controlCliente.buscarCliente(id);
            if (existente != null) {
                existente.setTipoDocumento(tipoDoc);
                existente.setNumeroDocumento(documento);
                existente.setNombre(nombre);
                existente.setApellido(apellido);
                existente.setTelefono(telefono);
                existente.setEmail(email);
                existente.setFechaNacimiento(fechaNac);
                existente.setFechaRegistro(fechaRegistro);

                if (controlCliente.actualizarCliente(existente)) {
                    mostrarAlerta("Éxito", "Cliente actualizado correctamente", Alert.AlertType.INFORMATION);
                    actualizarTabla();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el cliente", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "No se encontró el cliente", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID inválido", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void eliminarCliente(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            if (controlCliente.eliminarCliente(id)) {
                mostrarAlerta("Éxito", "Cliente eliminado correctamente", Alert.AlertType.INFORMATION);
                actualizarTabla();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el cliente", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID inválido", Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosFormulario(Cliente cliente, TextField txtId, ComboBox<String> cbTipoDoc,
            TextField txtDocumento, TextField txtNombre, TextField txtApellido,
            TextField txtTelefono, TextField txtEmail, DatePicker dpFechaNac,
            DatePicker dpFechaRegistro) {
        txtId.setText(String.valueOf(cliente.getIdcliente()));
        cbTipoDoc.setValue(cliente.getTipoDocumento());
        txtDocumento.setText(cliente.getNumeroDocumento());
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtTelefono.setText(cliente.getTelefono());
        txtEmail.setText(cliente.getEmail());
        if (cliente.getFechaNacimiento() != null) {
            dpFechaNac.setValue(cliente.getFechaNacimiento().toLocalDate());
        }
        if (cliente.getFechaRegistro() != null) {
            dpFechaRegistro.setValue(cliente.getFechaRegistro().toLocalDate());
        }
    }

    private void limpiarFormulario(TextField txtId, ComboBox<String> cbTipoDoc, TextField txtDocumento,
            TextField txtNombre, TextField txtApellido, TextField txtTelefono,
            TextField txtEmail, DatePicker dpFechaNac, DatePicker dpFechaRegistro) {
        txtId.clear();
        cbTipoDoc.getSelectionModel().clearSelection();
        txtDocumento.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEmail.clear();
        dpFechaNac.setValue(null);
        dpFechaRegistro.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void exportarClientesAExcel(List<Cliente> clientes) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clientes");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Tipo Doc", "Documento", "Nombre", "Apellido", "Teléfono", "Email", "Fecha Nac.", "Fecha Reg."};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowNum = 1;
        for (Cliente c : clientes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(c.getIdcliente());
            row.createCell(1).setCellValue(c.getTipoDocumento());
            row.createCell(2).setCellValue(c.getNumeroDocumento());
            row.createCell(3).setCellValue(c.getNombre());
            row.createCell(4).setCellValue(c.getApellido());
            row.createCell(5).setCellValue(c.getTelefono());
            row.createCell(6).setCellValue(c.getEmail());
            row.createCell(7).setCellValue(c.getFechaNacimiento().toString());
            row.createCell(8).setCellValue(c.getFechaRegistro().toString());
        }

        try (FileOutputStream fileOut = new FileOutputStream("clientes.xlsx")) {
            workbook.write(fileOut);
            mostrarAlerta("Éxito", "Clientes exportados correctamente a Excel", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al exportar Excel: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
