package Views;

import Controller.ControlLogin;
import Model.Trabajador;
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

public class LoginView extends Application {

    @Override
    public void start(Stage primaryStage) {
        ControlLogin controlLogin = new ControlLogin();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        VBox loginBox = new VBox(15);
        loginBox.setPadding(new Insets(40));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        loginBox.setMaxWidth(350);

        // Logo
        String logoUrl = "https://img.icons8.com/ios-filled/100/2c3e50/user-male-circle.png";
        ImageView logo = new ImageView(new Image(logoUrl, true));
        logo.setFitWidth(80);
        logo.setFitHeight(80);

        Label lblTitle = new Label("Iniciar Sesión");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.setMaxWidth(Double.MAX_VALUE);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.setMaxWidth(Double.MAX_VALUE);

        Button btnLogin = new Button("Ingresar");
        btnLogin.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");
        btnLogin.setMaxWidth(Double.MAX_VALUE);

        Label lblError = new Label();
        lblError.setTextFill(Color.RED);

        btnLogin.setOnAction(e -> {
            String user = txtUsuario.getText();
            String pass = txtPassword.getText();

            Trabajador trabajador = controlLogin.autenticar(user, pass);
            if (trabajador != null) {
                lblError.setText("");
                abrirMenuPrincipal(primaryStage);
            } else {
                lblError.setText("Usuario o contraseña incorrectos.");
            }
        });

        loginBox.getChildren().addAll(logo, lblTitle, txtUsuario, txtPassword, btnLogin, lblError);
        VBox.setMargin(btnLogin, new Insets(10, 0, 0, 0));

        StackPane center = new StackPane(loginBox);
        center.setAlignment(Pos.CENTER);
        root.setCenter(center);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Login - Sistema de Alquiler de Autos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void abrirMenuPrincipal(Stage primaryStage) {
        try {
            new MenuPrincipal().start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


