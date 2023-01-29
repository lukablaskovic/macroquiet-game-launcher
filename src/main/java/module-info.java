module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    requires java.net.http;
    requires java.datatransfer;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}