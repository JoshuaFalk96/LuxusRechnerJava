module org.example.luxusrechnerjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens org.example.luxusrechnerjava to javafx.fxml,javafx.graphics,com.fasterxml.jackson.databind;
}