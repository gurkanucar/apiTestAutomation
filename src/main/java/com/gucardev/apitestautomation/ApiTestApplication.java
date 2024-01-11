package com.gucardev.apitestautomation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApiTestApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApiTestApplication.class.getResource("apitest-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 740);
        stage.setTitle("Simple Test Automation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}