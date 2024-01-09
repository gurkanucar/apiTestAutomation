package com.gucardev.apitestautomation;

import com.gucardev.apitestautomation.model.TestScenario;
import com.gucardev.apitestautomation.service.DragAndDropHandler;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloController {

  @FXML private VBox dropZone;
  @FXML private ListView<TestScenario> testScenarioListView;
  private DragAndDropHandler dragAndDropHandler;

  @FXML
  public void initialize() {
    dragAndDropHandler = new DragAndDropHandler(dropZone, this);
    configureListView();
  }

  private void configureListView() {
    testScenarioListView.setCellFactory(
        new Callback<>() {
          @Override
          public ListCell<TestScenario> call(ListView<TestScenario> listView) {
            return new ListCell<>() {
              @Override
              protected void updateItem(TestScenario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText(null);
                  setStyle("");
                } else {
                  setText(item.toString());
                  setStyle(
                      "-fx-background-color: "
                          + (item.isCompleted() ? "lightgreen" : "salmon")
                          + ";");
                }
              }
            };
          }
        });
  }

  public void addTestScenarios(List<TestScenario> scenarios) {
    testScenarioListView.getItems().addAll(scenarios);
  }
}
