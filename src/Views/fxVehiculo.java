package Views;

import Controller.ControlVehiculo;
import Model.Categoria;
import Model.Vehiculo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class fxVehiculo extends Application {

    private ControlVehiculo control = new ControlVehiculo();
    private TableView<Vehiculo> tabla = new TableView<>();
    private ObservableList<Vehiculo> vehiculos;
    private ObservableList<Categoria> categorias;

    private ComboBox<Categoria> cboCategoria = new ComboBox<>();
    private TextField txtPlaca = new TextField();
    private TextField txtMarca = new TextField();
    private TextField txtDescripcion = new TextField();
    private TextField txtModelo = new TextField();
    private TextField txtTarifa = new TextField();
    private CheckBox chkDisponible = new CheckBox("Disponible");

    @Override
    public void start(Stage stage) {
        categorias = FXCollections.observableArrayList(control.obtenerCategorias());
        cboCategoria.setItems(categorias);

        TableColumn<Vehiculo, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPlaca()));

        TableColumn<Vehiculo, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getMarca()));

        TableColumn<Vehiculo, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getModelo()));

        TableColumn<Vehiculo, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCategoria().getTipoVehiculo()));

        tabla.getColumns().addAll(colPlaca, colMarca, colModelo, colCategoria);
        actualizarTabla();

        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Fila 0
        form.add(new Label("Categoría:"), 0, 0);
        form.add(cboCategoria, 1, 0);
        form.add(new Label("Placa:"), 2, 0);
        form.add(txtPlaca, 3, 0);

        // Fila 1
        form.add(new Label("Marca:"), 0, 1);
        form.add(txtMarca, 1, 1);
        form.add(new Label("Descripción:"), 2, 1);
        form.add(txtDescripcion, 3, 1);

        // Fila 2
        form.add(new Label("Modelo:"), 0, 2);
        form.add(txtModelo, 1, 2);
        form.add(new Label("Tarifa diaria:"), 2, 2);
        form.add(txtTarifa, 3, 2);

        // Fila 3
        form.add(chkDisponible, 1, 3);

        Button btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarVehiculo());

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarVehiculo());

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarVehiculo());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar, btnLimpiar);
        botones.setPadding(new Insets(10));

        tabla.setOnMouseClicked(e -> {
            Vehiculo v = tabla.getSelectionModel().getSelectedItem();
            if (v != null) {
                cboCategoria.setValue(v.getCategoria());
                txtPlaca.setText(v.getPlaca());
                txtMarca.setText(v.getMarca());
                txtDescripcion.setText(v.getDescripcion());
                txtModelo.setText(v.getModelo());
                txtTarifa.setText(String.valueOf(v.getTarifaDiaria()));
                chkDisponible.setSelected(v.isDisponibilidad());
            }
        });

        VBox root = new VBox(10, form, botones, tabla);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Vehículos");
        stage.show();
    }

    private void agregarVehiculo() {
        Vehiculo v = obtenerVehiculoDesdeFormulario(0);
        if (control.agregarVehiculo(v)) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarVehiculo() {
        Vehiculo seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Vehiculo v = obtenerVehiculoDesdeFormulario(seleccionado.getIdvehiculo());
            if (control.actualizarVehiculo(v)) {
                actualizarTabla();
                limpiarFormulario();
            }
        }
    }

    private void eliminarVehiculo() {
        Vehiculo seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null && control.eliminarVehiculo(seleccionado.getIdvehiculo())) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private Vehiculo obtenerVehiculoDesdeFormulario(int id) {
        Categoria cat = cboCategoria.getValue();
        return new Vehiculo(
                id,
                cat,
                txtPlaca.getText(),
                txtMarca.getText(),
                txtDescripcion.getText(),
                txtModelo.getText(),
                Double.parseDouble(txtTarifa.getText()),
                chkDisponible.isSelected()
        );
    }

    private void actualizarTabla() {
        vehiculos = FXCollections.observableArrayList(control.listarVehiculos());
        tabla.setItems(vehiculos);
    }

    private void limpiarFormulario() {
        cboCategoria.setValue(null);
        txtPlaca.clear();
        txtMarca.clear();
        txtDescripcion.clear();
        txtModelo.clear();
        txtTarifa.clear();
        chkDisponible.setSelected(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

