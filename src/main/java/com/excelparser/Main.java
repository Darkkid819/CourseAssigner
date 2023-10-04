package com.excelparser;

import com.excelparser.model.CourseSet;
import com.excelparser.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void init(String[] args) {
        DataManager.loadData(args);
        System.out.println(CourseSet.getInstance().toString()); // test
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
        stage.setResizable(false); // Temporary

        stage.setOnCloseRequest(e -> DataManager.saveData());
    }

    public static void main(String[] args) {
        init(args);
        launch(args);
    }
}


