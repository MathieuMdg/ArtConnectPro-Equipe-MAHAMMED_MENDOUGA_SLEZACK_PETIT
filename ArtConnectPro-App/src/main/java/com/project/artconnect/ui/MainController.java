package com.project.artconnect.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.application.Platform;
import com.project.artconnect.service.AuthService;
import javafx.scene.control.Menu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private AuthService authService;

    @FXML
    private TabPane mainTabPane;

    @FXML
    public void initialize() {
        // Initialization logic if needed
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private Menu adminMenu;

    public void setAuthService(AuthService authService) {

        this.authService = authService;

        updateAdminUI();
    }

    private void updateAdminUI() {

        if (authService == null) {
            return;
        }

        adminMenu.setVisible(authService.isAdmin());
    }

    @FXML
    private void openAdminPanel() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/project/artconnect/ui/AdminView.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 600);

            Stage stage = new Stage();

            stage.setTitle("Panneau Administrateur");

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
