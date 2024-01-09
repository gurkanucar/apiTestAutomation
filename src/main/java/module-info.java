module com.gucardev.apitestautomation {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires org.apache.poi.poi;

    requires org.slf4j;
    requires org.apache.poi.ooxml;

    opens com.gucardev.apitestautomation to javafx.fxml;
    exports com.gucardev.apitestautomation;
}