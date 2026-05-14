package com.project.artconnect.ui;

import com.project.artconnect.model.Workshop;
import com.project.artconnect.service.WorkshopRegistrationService;
import com.project.artconnect.service.WorkshopService;
import com.project.artconnect.util.ServiceProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDateTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class WorkshopController {
    private final WorkshopRegistrationService registrationService =
        new WorkshopRegistrationService();
    @FXML
    private TableView<Workshop> workshopTable;
    @FXML
    private TableColumn<Workshop, String> titleColumn;
    @FXML
    private TableColumn<Workshop, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Workshop, String> instructorColumn;
    @FXML
    private TableColumn<Workshop, Double> priceColumn;
    @FXML
    private TableColumn<Workshop, String> levelColumn;

    private Timeline autoRefreshTimeline;

    private final WorkshopService workshopService = ServiceProvider.getWorkshopService();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        instructorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getInstructor() != null ? cellData.getValue().getInstructor().getName()
                        : "Unknown"));

        refreshTable();
        startAutoRefresh();
    }

    private void refreshTable() {
        workshopTable.setItems(FXCollections.observableArrayList(workshopService.getAllWorkshops()));
    }

    private void startAutoRefresh() {
        autoRefreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> refreshTable()));
        autoRefreshTimeline.setCycleCount(Timeline.INDEFINITE);
        autoRefreshTimeline.play();
    }
    @FXML
    private void handleRegisterWorkshop() {

        Workshop selectedWorkshop =
                workshopTable.getSelectionModel().getSelectedItem();

        if(selectedWorkshop == null) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "No selection",
                    "Please select a workshop."
            );

            return;
        }

        boolean success =
                registrationService.registerToWorkshop(
                        selectedWorkshop.getWorkshopId()
                );

        if(success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Registration successful",
                    "You are registered to the workshop."
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Registration failed",
                    "You may already be registered."
            );
        }
    }
    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }


}
