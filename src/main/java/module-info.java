module com.example.graphs_maven {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;


    opens com.example to javafx.fxml;
    exports com.example;
}