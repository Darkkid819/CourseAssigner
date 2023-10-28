package com.excelparser.controller;

import com.excelparser.Main;
import com.excelparser.util.ConfigurationManager;
import com.excelparser.util.DataManager;
import com.excelparser.util.ViewManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImportController {

    @FXML
    private Button coursesButton;

    @FXML
    private TextField coursesPathField;

    @FXML
    private Button frequencyButton;

    @FXML
    private TextField frequencyPathField;

    @FXML
    private Button importButton;

    @FXML
    private Button instructorsButton;

    @FXML
    private TextField instructorsPathField;

    @FXML
    private StackPane stack;

    @FXML
    void browseFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("data"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        File selectedFile = fileChooser.showOpenDialog(stack.getScene().getWindow());
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            if (event.getSource() == coursesButton) {
                coursesPathField.setText(path);
            } else if (event.getSource() == frequencyButton) {
                frequencyPathField.setText(path);
            } else if (event.getSource() == instructorsButton) {
                instructorsPathField.setText(path);
            }
        }
    }

    @FXML
    void importData(ActionEvent event) throws IOException {
        if (coursesPathField.getText().isEmpty() ||
                frequencyPathField.getText().isEmpty() ||
                instructorsPathField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Import Error");
            alert.setHeaderText("Path Missing");
            alert.setContentText("Please ensure all file paths are provided before importing.");
            alert.showAndWait();
        } else load();
    }

    private void load() {
        switchView();
        Task<Void> loadDataTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ConfigurationManager.configure(instructorsPathField.getText(),
                        coursesPathField.getText(),
                        frequencyPathField.getText());
                DataManager.loadData();
                return null;
            }
        };
        loadDataTask.setOnSucceeded(event -> close());
        loadDataTask.setOnFailed(event ->{
            switchView();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Import Error");
            alert.setHeaderText("Failed to Import");
            alert.setContentText("There was an error during import. Please check the provided files and try again.");
            alert.showAndWait();
        });

        new Thread(loadDataTask).start();
    }

    // switches between import view and progress view
    private void switchView() {
        for (Node node: stack.getChildren()) {
            node.setVisible(!node.isVisible());
        }
    }

    private void close() {
        Stage currentStage = (Stage) importButton.getScene().getWindow();
        currentStage.close();
        ViewManager.loadMainView();
    }
}
