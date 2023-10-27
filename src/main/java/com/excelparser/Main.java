package com.excelparser;

import com.excelparser.model.InstructorSet;
import com.excelparser.model.SectionSet;
import com.excelparser.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void init(String[] args) {
        DataManager.loadData(args);
        System.out.println(SectionSet.getInstance().toString()); // test
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/instructors.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Excel Parser");
        stage.setScene(scene);
        stage.show();

        // set minimum window size
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());

        stage.setOnCloseRequest(e -> DataManager.saveData());
    }

    public static void main(String[] args) {
        init(args);
        launch(args);
    }
}


