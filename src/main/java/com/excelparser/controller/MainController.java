package com.excelparser.controller;

import com.excelparser.model.*;
import com.excelparser.model.enums.Campus;
import com.excelparser.model.enums.Day;
import com.excelparser.model.enums.InstructionMethod;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    private static final String AVAILABLE_COLOR = "#008000";
    private static final String UNAVAILABLE_COLOR = "#EB4034";

    InstructorSet instructorSet;
    private Button[][] timeSlotButtons;

    @FXML
    private Label campusLabel;

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
    private TableView<Section> sectionTableView;

    @FXML
    private TableColumn<Section, String> courseTitleColumn;

    @FXML
    private TableColumn<Section, String> campusColumn;

    @FXML
    private TableColumn<Section, String> instructionMethodColumn;

    @FXML
    private TableColumn<Section, String> partOfTermColumn;

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TableColumn<Course, String> courseColumn;

    @FXML
    private TableColumn<Course, String> daysColumn;

    @FXML
    private TableColumn<Course, String> startTimeColumn;

    @FXML
    private TableColumn<Course, String> endTimeColumn;

    @FXML
    private TableColumn<Course, String> startDateColumn;

    @FXML
    private TableColumn<Course, String> endDateColumn;

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
        initializeSectionTableView();
    }

    private void initializeSpinner() {
        ObservableList<Instructor> options = SeniorityList.getInstance().toObservableList();
        SpinnerValueFactory<Instructor> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        nameSpinner.setValueFactory(valueFactory);
        nameSpinner.valueProperty().addListener((observableValue, instructor, t1) -> {
            courseTableView.getItems().clear();
            sectionTableView.scrollTo(0);
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

    private void initializeSectionTableView() {
        courseTitleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCourseTitle()));
        campusColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCampus().toString()));
        instructionMethodColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getInstructionMethod().toString()));
        partOfTermColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPartOfTerm().toString()));

        nameSpinner.valueProperty().addListener((observable, oldValue, newValue) -> updateSectionTableView(newValue));
        updateSectionTableView(nameSpinner.getValue());

        sectionTableView.setRowFactory(tv -> {
            TableRow<Section> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    Section clickedItem = row.getItem();
                    initializeCourseTableView(clickedItem);
                }
            });
            return row;
        });
    }

    // a lot of magic going on here
    private void updateSectionTableView(Instructor instructor) {
        Map<String, Integer> frequencies = instructor.getFrequencies().getAllFrequencies()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        Map.Entry::getValue
                ));

        boolean[] daysAvailable = getDaysAvailable(instructor);
        boolean[][] timeAvailability = instructor.getAvailability();
        List<Campus> preferredCampuses = instructor.getInstructorInfo().getPreferredCampuses();

        List<Section> filteredSections = SectionSet.getInstance().toList().stream()
                .filter(section -> { // filter sections not taught by instructor
                    String courseName = section.getCourseList().get(0).toString();
                    return instructor.getInstructorInfo().getCoursesCertified().contains(courseName);
                })
                .filter(section -> { // filter sections not taught online by instructor
                    return !section.getInstructionMethod().name().equals("BLBD") || instructor.getInstructorInfo().isOnlineCertified();
                })
                .filter(section -> { // filter sections based on instructor availability
                    return section.getCourseList().stream().allMatch(course -> {
                        for (Day day : course.getDays()) {
                            int dayIndex = day.ordinal();
                            if (day != Day.O && !daysAvailable[dayIndex]) {
                                return false;
                            }

                            TimeRange timeRange = course.getTimeRange();
                            if (timeRange != null) {
                                int startPeriod = getTimePeriodIndex(timeRange.getStart());
                                int endPeriod = getTimePeriodIndex(timeRange.getEnd());
                                for (int i = startPeriod; i <= endPeriod; i++) {
                                    if (!timeAvailability[i][dayIndex]) { // Check if any of the periods during the course's time range is unavailable
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    });
                })
                .filter(section -> {
                    if (section.getInstructionMethod().name().equals("BLBD")) {
                        return preferredCampuses.contains(Campus.O);
                    }
                    return preferredCampuses.contains(section.getCampus());
                })
                .sorted(
                        Comparator.comparing((Section section) -> frequencies.getOrDefault(section.getCourseList().get(0).toString(), Integer.MIN_VALUE), Comparator.reverseOrder())
                                .thenComparing(section -> section.getCourseList().get(0).toString())
                                .thenComparing(section -> {
                                    if (section.getInstructionMethod().toString().contains("Online")) {
                                        return preferredCampuses.indexOf(Campus.O);
                                    }
                                    return preferredCampuses.indexOf(section.getCampus());
                                })
                )
                .collect(Collectors.toList());

        ObservableList<Section> data = FXCollections.observableList(filteredSections);
        sectionTableView.setItems(data);
    }

    private void initializeCourseTableView(Section section) {
        courseColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().toString()));
        daysColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDays().stream().map(Day::toString).collect(Collectors.joining(", "))));
        startTimeColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTimeRange() == null || cellData.getValue().getTimeRange().getStart() == null) {
                return new ReadOnlyStringWrapper("");
            } else {
                return new ReadOnlyStringWrapper(cellData.getValue().getTimeRange().getFormattedStart());
            }
        });
        endTimeColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTimeRange() == null || cellData.getValue().getTimeRange().getEnd() == null) {
                return new ReadOnlyStringWrapper("");
            } else {
                return new ReadOnlyStringWrapper(cellData.getValue().getTimeRange().getFormattedEnd());
            }
        });
        startDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDateRange().getFormattedStart()));
        endDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDateRange().getFormattedEnd()));

        updateCourseTableView(section, nameSpinner.getValue());
    }

    private void updateCourseTableView(Section section, Instructor instructor) {
        Instructor currentInstructor = nameSpinner.getValue();
        boolean[] daysAvailable = getDaysAvailable(currentInstructor);
        boolean[][] timeAvailability = instructor.getAvailability(); // Assuming this method exists

        List<Course> filteredCourses = section.getCourseList().stream()
                .filter(course -> {
                    for (Day day : course.getDays()) {
                        int dayIndex = day.ordinal();
                        if (day != Day.O && !daysAvailable[dayIndex]) { // Skip "Online" and check day availability
                            return false;
                        }

                        TimeRange timeRange = course.getTimeRange();
                        if (timeRange != null) {
                            int startPeriod = getTimePeriodIndex(timeRange.getStart());
                            int endPeriod = getTimePeriodIndex(timeRange.getEnd());
                            for (int i = startPeriod; i <= endPeriod; i++) {
                                if (!timeAvailability[i][dayIndex]) { // Check if any of the periods during the course's time range is unavailable
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        ObservableList<Course> data = FXCollections.observableList(filteredCourses);
        courseTableView.setItems(data);
    }

    private int getTimePeriodIndex(LocalTime time) {
        if (time.isBefore(LocalTime.of(8, 0))) return 0;
        if (time.isBefore(LocalTime.of(12, 0))) return 1;
        if (time.isBefore(LocalTime.of(15, 0))) return 2;
        if (time.isBefore(LocalTime.of(16, 0))) return 3;
        if (time.isBefore(LocalTime.of(18, 0))) return 4;
        return 5;
    }

    private boolean[] getDaysAvailable(Instructor currentInstructor) {
        boolean[] daysAvailable = new boolean[7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) { // Iterate over periods
                if (currentInstructor.getAvailability()[j][i]) {
                    daysAvailable[i] = true;
                    break;
                }
            }
        }
        return daysAvailable;
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
