package com.excelparser.controller;

import com.excelparser.Main;
import com.excelparser.model.Instructor;
import com.excelparser.model.InstructorList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class MainController implements Initializable {

    Instructor[] instructors;
    int currentInstructor = 0;
    private Button[][] timeSlotButtons;
    @FXML
    TextField idTextField;
    @FXML
    Label nameLabel;
    @FXML
    Label idLabel;
    @FXML
    Label rankLabel;
    @FXML
    Button monday7to8;
    @FXML
    Label homeCampusLabel;
    @FXML
    Label preferredCampusLabel;
    @FXML
    Label onlineCertifiedLabel;
    @FXML
    Label coursesCertifiedLabel;
    @FXML
    Label coursesRequestedLabel;

    @FXML Button monday8to12;
    @FXML Button monday12to3;
    @FXML Button monday3to4;
    @FXML Button monday4to6;
    @FXML Button monday6to10;

    @FXML Button tuesday7to8;
    @FXML Button tuesday8to12;
    @FXML Button tuesday12to3;
    @FXML Button tuesday3to4;
    @FXML Button tuesday4to6;
    @FXML Button tuesday6to10;

    @FXML Button wednesday7to8;
    @FXML Button wednesday8to12;
    @FXML Button wednesday12to3;
    @FXML Button wednesday3to4;
    @FXML Button wednesday4to6;
    @FXML Button wednesday6to10;

    @FXML Button thursday7to8;
    @FXML Button thursday8to12;
    @FXML Button thursday12to3;
    @FXML Button thursday3to4;
    @FXML Button thursday4to6;
    @FXML Button thursday6to10;

    @FXML Button friday7to8;
    @FXML Button friday8to12;
    @FXML Button friday12to3;
    @FXML Button friday3to4;
    @FXML Button friday4to6;
    @FXML Button friday6to10;

    @FXML Button saturday7to8;
    @FXML Button saturday8to12;
    @FXML Button saturday12to3;
    @FXML Button saturday3to4;
    @FXML Button saturday4to6;
    @FXML Button saturday6to10;

    @FXML Button sunday7to8;
    @FXML Button sunday8to12;
    @FXML Button sunday12to3;
    @FXML Button sunday3to4;
    @FXML Button sunday4to6;
    @FXML Button sunday6to10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instructors = InstructorList.getInstance().getInstructorList().toArray(new Instructor[0]);

        timeSlotButtons = new Button[][]{
                {monday7to8, tuesday7to8, wednesday7to8, thursday7to8, friday7to8, saturday7to8, sunday7to8},
                {monday8to12, tuesday8to12, wednesday8to12, thursday8to12, friday8to12, saturday8to12, sunday8to12},
                {monday12to3, tuesday12to3, wednesday12to3, thursday12to3, friday12to3, saturday12to3, sunday12to3},
                {monday3to4, tuesday3to4, wednesday3to4, thursday3to4, friday3to4, saturday3to4, sunday3to4},
                {monday4to6, tuesday4to6, wednesday4to6, thursday4to6, friday4to6, saturday4to6, sunday4to6},
                {monday6to10, tuesday6to10, wednesday6to10, thursday6to10, friday6to10, saturday6to10, sunday6to10}
        };

        updateInstructor();
    }

    public void nextInstructor() {
        if (currentInstructor < instructors.length - 1) {
            currentInstructor++;
            updateInstructor();
        }
    }

    public void previousInstructor() {
        if (currentInstructor > 0) {
            currentInstructor--;
            updateInstructor();
        }
    }

    private void updateInstructor() {
        nameLabel.setText(instructors[currentInstructor].getName());
        idLabel.setText(instructors[currentInstructor].getId());
        rankLabel.setText(instructors[currentInstructor].getRank());
        homeCampusLabel.setText(instructors[currentInstructor].getHomeCampus());
        preferredCampusLabel.setText(instructors[currentInstructor].getPreferredCampuses());
        onlineCertifiedLabel.setText((instructors[currentInstructor].isOnlineCertified() ? "Yes" : "No"));
        coursesCertifiedLabel.setText(instructors[currentInstructor].getCoursesCertified());
        coursesRequestedLabel.setText(String.valueOf(instructors[currentInstructor].getCoursesRequested()));
        updateAvailability();
    }

    public void updateAvailability() {
        Instructor instructor = instructors[currentInstructor];
        boolean [][] availability = instructor.getAvailability();

        // Loop through the timeSlotButtons and update their colors based on availability
        for (int slot = 0; slot < timeSlotButtons.length; slot++) {
            for (int day = 0; day < timeSlotButtons[slot].length; day++) {
                if (availability[slot][day]) {
                    timeSlotButtons[slot][day].setStyle("-fx-background-color: #008000;");
                } else {
                    timeSlotButtons[slot][day].setStyle("-fx-background-color: #E6E6E6");
                }
            }
        }
    }

    public void searchByID() {
        String idText = idTextField.getText();

        if (idText.isEmpty()) {
            showErrorDialog("Please enter an ID.");
        } else {
            try {
                int id = Integer.parseInt(idText);

                TreeSet<Instructor> instructorSet = InstructorList.getInstance().getInstructorList();

                Optional<Instructor> foundInstructor = instructorSet.stream()
                        .filter(instructor -> Integer.parseInt(instructor.getId()) == id)
                        .findFirst();

                if (foundInstructor.isPresent()) {
                    currentInstructor = instructorSet.headSet(foundInstructor.get(), false).size();
                    updateInstructor();
                } else {
                    showErrorDialog("No instructor found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                showErrorDialog("Invalid ID: " + idText);
            }
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
