package com.project.artconnect;

import com.project.artconnect.config.DatabaseConfig;
import com.project.artconnect.dao.UserDao;
import com.project.artconnect.persistence.JdbcUserDao;
import com.project.artconnect.service.AuthService;
import com.project.artconnect.ui.LoginView;
import com.project.artconnect.ui.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // =====================================================
        // DATABASE CONNECTION
        // =====================================================

        Connection connection =
                DatabaseConfig.getConnection();

        UserDao userDao =
                new JdbcUserDao(connection);

        AuthService authService =
                new AuthService(userDao);

        // =====================================================
        // LOGIN WINDOW
        // =====================================================

        LoginView loginView =
                new LoginView(authService);

        loginView.setVisible(true);

        // =====================================================
        // LOGIN FAILED
        // =====================================================

        if (!loginView.isLoginSuccessful()) {

            System.exit(0);

            return;
        }

        // =====================================================
        // LOAD MAIN VIEW
        // =====================================================

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/com/project/artconnect/ui/MainView.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        1200,
                        800
                );

        MainController controller =
                loader.getController();

        controller.setAuthService(authService);

        // =====================================================
        // STAGE
        // =====================================================

        stage.setTitle(
                "ArtConnect Pro - Local Art Community Platform"
        );

        stage.setScene(scene);

        stage.show();

        // =====================================================
        // DEBUG ROLE
        // =====================================================

        if (authService.isAdmin()) {

            System.out.println(
                    "Connexion ADMIN"
            );

        } else {

            System.out.println(
                    "Connexion USER"
            );
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}