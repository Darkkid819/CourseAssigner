package com.excelparser;

import com.excelparser.model.InstructorSet;
import com.excelparser.model.SectionSet;
import com.excelparser.util.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        if (ConfigurationManager.serializedFilesExist()) {
            DataManager.loadData();
            ViewManager.loadMainView();
        } else {
            ViewManager.loadImportView();
        }
    }

    public static void main(String[] args) {launch(args);}
}


