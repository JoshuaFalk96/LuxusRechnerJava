module org.example.luxusrechnerjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.luxusrechnerjava to javafx.fxml;
    exports org.example.luxusrechnerjava;
}