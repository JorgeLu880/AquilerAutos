package Views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        MenuBar menuBar = createMenuBar();
        VBox sidebar = createSidebar();
        StackPane contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));
        contentArea.setStyle("-fx-background-color: #f5f5f5;");
        HBox footer = createFooter();

        root.setTop(menuBar);
        root.setLeft(sidebar);
        root.setCenter(contentArea);
        root.setBottom(footer);

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setTitle("Sistema de Alquiler de Autos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        Menu menuArchivo = new Menu("Archivo");
        MenuItem salirItem = new MenuItem("Salir");
        salirItem.setOnAction(e -> System.exit(0));
        menuArchivo.getItems().add(salirItem);

        Menu menuAyuda = new Menu("Ayuda");
        MenuItem acercaDeItem = new MenuItem("Acerca de");
        menuAyuda.getItems().add(acercaDeItem);

        return new MenuBar(menuArchivo, menuAyuda);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(200);

        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER);
        String logoUrl = "https://img.icons8.com/ios-filled/100/ffffff/car.png";
        try {
            ImageView logo = new ImageView(new Image(logoUrl, true));
            logo.setFitWidth(80);
            logo.setFitHeight(80);
            logoBox.getChildren().add(logo);
        } catch (Exception ex) {
            System.out.println("Error al cargar el logo: " + ex.getMessage());
        }

        Button btnClientes = createMenuButton("Clientes", "https://img.icons8.com/ios-glyphs/30/ffffff/user--v1.png");
        Button btnTrabajadores = createMenuButton("Trabajadores", "https://img.icons8.com/ios-filled/30/ffffff/worker-male.png");
        Button btnVehiculos = createMenuButton("Vehículos", "https://img.icons8.com/ios-filled/30/ffffff/car.png");
        Button btnAlquileres = createMenuButton("Alquileres", "https://img.icons8.com/ios-filled/30/ffffff/rent-car.png");
        Button btnDevoluciones = createMenuButton("Devoluciones", "https://img.icons8.com/ios-filled/30/ffffff/return.png");
        Button btnReportes = createMenuButton("Reportes", "https://img.icons8.com/ios-filled/30/ffffff/bar-chart.png");

        btnClientes.setOnAction(e -> {
            FxCliente fx = new FxCliente();
            try {
                fx.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnTrabajadores.setOnAction(e -> {
            FxTrabajador fx = new FxTrabajador();
            try {
                fx.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnVehiculos.setOnAction(e -> {
            fxVehiculo fx = new fxVehiculo();
            try {
                fx.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));

        sidebar.getChildren().addAll(
                logoBox,
                btnClientes,
                btnTrabajadores,
                btnVehiculos,
                btnAlquileres,
                btnDevoluciones,
                separator,
                btnReportes
        );

        return sidebar;
    }

    private Button createMenuButton(String text, String iconUrl) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: center-left;");
        button.setPadding(new Insets(10, 15, 10, 15));

        try {
            Image img = new Image(iconUrl, true);
            ImageView icon = new ImageView(img);
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            button.setGraphic(icon);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + iconUrl);
        }

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: center-left;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: center-left;"));

        return button;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #34495e;");
        footer.setAlignment(Pos.CENTER);

        Label copyright = new Label("© 2023 Sistema de Alquiler de Autos - Todos los derechos reservados");
        copyright.setTextFill(Color.WHITE);

        footer.getChildren().add(copyright);
        return footer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
