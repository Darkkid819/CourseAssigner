package com.excelparser.util;

import com.excelparser.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class ViewManager {

    private static final String MAIN_VIEW_PATH = "views/main.fxml";
    private static final String IMPORT_VIEW_PATH = "views/import.fxml";

    private ViewManager() {}

    public static void loadMainView() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(MAIN_VIEW_PATH));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Excel Parser");
            stage.setScene(scene);
            stage.show();

            // set minimum window size
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());

            stage.setOnCloseRequest(e -> DataManager.saveData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadImportView() {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(IMPORT_VIEW_PATH));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Import Data");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
