module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires java.net.http;
    requires java.datatransfer;
    requires java.desktop;

    requires org.json;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}