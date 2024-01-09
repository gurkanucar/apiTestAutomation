module com.gucardev.apitestautomation {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.gucardev.apitestautomation to javafx.fxml;
    exports com.gucardev.apitestautomation;
}