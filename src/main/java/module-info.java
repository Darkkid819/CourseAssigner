module com.excelparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.excelparser to javafx.fxml;
    exports com.excelparser;
    exports com.excelparser.controller;
    opens com.excelparser.controller to javafx.fxml;
}