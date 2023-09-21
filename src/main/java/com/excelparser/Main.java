package com.excelparser;

import com.excelparser.model.InstructorList;
import com.excelparser.util.XLSXParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main extends Application {
    private static Scanner scanner;
    private static String filePath;

    public static void init(String[] args) {
        getInputFilePath(args);
        InstructorList.getInstance();
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

    private static void getInputFilePath(String[] args) {
        // Check if a command-line argument for the file path is provided
        if (args.length > 0) {
            filePath = args[0];
        } else {
            while (filePath == null || !isValidFilePath(filePath)) {
                System.out.print("Enter the XLSX file path: ");
                filePath = scanner.nextLine();
            }
        }
    }

    private static boolean isValidFilePath(String path) {
        return Files.exists(Paths.get(path)) && path.toLowerCase().endsWith(".xlsx");
    }

    private static void parseInstructors() {
        try {
            XLSXParser.parse(filePath);
            System.out.println("Instructor data parsed successfully:\n");
            // System.out.println(instructors.toString());
        } catch (IOException e) {
            System.err.println("Error reading or parsing the xlsx file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        init(args);

        launch(args);
    }
}


