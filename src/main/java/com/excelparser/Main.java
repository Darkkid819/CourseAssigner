package com.excelparser;

import com.excelparser.model.CourseSet;
import com.excelparser.util.Config;
import com.excelparser.util.CourseProcessor;
import com.excelparser.util.InstructorProcessor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    public static void init(String[] args) {
        Config.configure(args);
        parseCourses();
        parseInstructors();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Excel Parser");
        stage.setScene(scene);
        stage.show();

        // set minimum window size
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
    }

    private static void parseInstructors() {
        try {
            InstructorProcessor.processInstructors(Config.getInstructorPath());
            System.out.println("Instructor data parsed successfully\n");
        } catch (IOException e) {
            System.err.println("Error reading or parsing the xlsx file\n");
            e.printStackTrace();
        }
    }

    private static void parseCourses() {
        try {
            CourseProcessor.processCourses(Config.getCoursePath());
            System.out.println("Course data parsed successfully\n");
            System.out.println(CourseSet.getInstance().toString()); // test
        } catch (IOException e) {
            System.err.println("Error reading or parsing the csv file\n");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        init(args);
        launch(args);
    }
}


