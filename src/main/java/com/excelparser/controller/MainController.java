package com.excelparser.controller;

import com.excelparser.model.Instructor;
import com.excelparser.model.InstructorInfo;
import com.excelparser.model.InstructorSet;
import com.excelparser.model.SeniorityList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String AVAILABLE_COLOR = "#008000";
    private static final String UNAVAILABLE_COLOR = "#EB4034";

    InstructorSet instructorSet;
    private Button[][] timeSlotButtons;

    @FXML
    private Label campusLabel;

    @FXML
    private TableView<?> courseTableView;

    @FXML
    private Button finalizeButton;

    @FXML
    private Button friday12to3;

    @FXML
    private Button friday3to4;

    @FXML
    private Button friday4to6;

    @FXML
    private Button friday6to10;

    @FXML
    private Button friday7to8;

    @FXML
    private Button friday8to12;

    @FXML
    private Label idLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private Button monday12to3;

    @FXML
    private Button monday3to4;

    @FXML
    private Button monday4to6;

    @FXML
    private Button monday6to10;

    @FXML
    private Button monday7to8;

    @FXML
    private Button monday8to12;

    @FXML
    private Label nameLabel;

    @FXML
    private Spinner<Instructor> nameSpinner;

    @FXML
    private Label rankLabel;

    @FXML
    private Button saturday12to3;

    @FXML
    private Button saturday3to4;

    @FXML
    private Button saturday4to6;

    @FXML
    private Button saturday6to10;

    @FXML
    private Button saturday7to8;

    @FXML
    private Button saturday8to12;

    @FXML
    private TableView<?> sectionTableView;

    @FXML
    private Button sunday12to3;

    @FXML
    private Button sunday3to4;

    @FXML
    private Button sunday4to6;

    @FXML
    private Button sunday6to10;

    @FXML
    private Button sunday7to8;

    @FXML
    private Button sunday8to12;

    @FXML
    private Button thursday12to3;

    @FXML
    private Button thursday3to4;

    @FXML
    private Button thursday4to6;

    @FXML
    private Button thursday6to10;

    @FXML
    private Button thursday7to8;

    @FXML
    private Button thursday8to12;

    @FXML
    private Button tuesday12to3;

    @FXML
    private Button tuesday3to4;

    @FXML
    private Button tuesday4to6;

    @FXML
    private Button tuesday6to10;

    @FXML
    private Button tuesday7to8;

    @FXML
    private Button tuesday8to12;

    @FXML
    private Button wednesday12to3;

    @FXML
    private Button wednesday3to4;

    @FXML
    private Button wednesday4to6;

    @FXML
    private Button wednesday6to10;

    @FXML
    private Button wednesday7to8;

    @FXML
    private Button wednesday8to12;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instructorSet = InstructorSet.getInstance();
        initializeTimeSlotButtons();
        initializeSpinner();
    }

    private void initializeSpinner() {
        ObservableList<Instructor> options = SeniorityList.getInstance().toObservableList();
        SpinnerValueFactory<Instructor> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        nameSpinner.setValueFactory(valueFactory);
        nameSpinner.valueProperty().addListener((observableValue, instructor, t1) -> {
            updateInstructor();
        });
        updateInstructor(); // initialize
    }

    private void initializeTimeSlotButtons() {
        timeSlotButtons = new Button[][]{
                {monday7to8, tuesday7to8, wednesday7to8, thursday7to8, friday7to8, saturday7to8, sunday7to8},
                {monday8to12, tuesday8to12, wednesday8to12, thursday8to12, friday8to12, saturday8to12, sunday8to12},
                {monday12to3, tuesday12to3, wednesday12to3, thursday12to3, friday12to3, saturday12to3, sunday12to3},
                {monday3to4, tuesday3to4, wednesday3to4, thursday3to4, friday3to4, saturday3to4, sunday3to4},
                {monday4to6, tuesday4to6, wednesday4to6, thursday4to6, friday4to6, saturday4to6, sunday4to6},
                {monday6to10, tuesday6to10, wednesday6to10, thursday6to10, friday6to10, saturday6to10, sunday6to10}
        };
    }

    private void updateInstructor() {
        nameLabel.setText(nameSpinner.getValue().getName().toString());
        idLabel.setText(nameSpinner.getValue().getId());
        rankLabel.setText(nameSpinner.getValue().getInstructorInfo().getRank().toString());
        campusLabel.setText(nameSpinner.getValue().getHomeCampus());
        updateAvailability();
    }

    public void updateAvailability() {
        Instructor instructor = nameSpinner.getValue();
        boolean[][] availability = instructor.getAvailability();

        for (int slot = 0; slot < timeSlotButtons.length; slot++) {
            for (int day = 0; day < timeSlotButtons[slot].length; day++) {
                updateButtonColor(timeSlotButtons[slot][day], availability[slot][day]);
            }
        }
    }

    private void updateButtonColor(Button button, boolean available) {
        button.setStyle("-fx-background-color: " + (available ? AVAILABLE_COLOR : UNAVAILABLE_COLOR) + ";");
    }

    public void searchByID() {
        String idText = idTextField.getText().trim();

        if (idText.isEmpty()) {
            showErrorDialog("Please enter an ID.");
            return;
        }

        // Check if the ID is numeric and of length 8
        if (!idText.matches("\\d{8}")) {
            showErrorDialog("Invalid ID format. Please enter a valid 8-digit numeric ID.");
            return;
        }

        Optional<Instructor> foundInstructor = instructorSet.search(idText);
        foundInstructor.ifPresentOrElse(instructor -> {
            nameSpinner.getValueFactory().setValue(instructor);
            updateInstructor();
        }, () -> showErrorDialog("No instructor found with ID: " + idText));
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void addCourse(ActionEvent event) {

    }

    @FXML
    void finalize(ActionEvent event) {

    }

    @FXML
    void removeCourse(ActionEvent event) {

    }
}
