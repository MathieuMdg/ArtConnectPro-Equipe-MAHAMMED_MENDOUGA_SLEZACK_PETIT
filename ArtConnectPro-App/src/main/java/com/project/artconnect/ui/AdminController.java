package com.project.artconnect.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.project.artconnect.config.DatabaseConfig;

public class AdminController {

    @FXML
    private ListView<String> artistListView;

    @FXML
    private void loadArtists() {

        artistListView.getItems().clear();

        String sql = "SELECT name FROM artist";

        try (
                Connection connection = DatabaseConfig.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql);

                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                artistListView.getItems().add(
                        rs.getString("name")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}