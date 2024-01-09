package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class TestScenarioListViewManager {

  private final ListView<TestScenario> listView;

  public TestScenarioListViewManager(ListView<TestScenario> listView) {
    this.listView = listView;
    configureListView();
  }

  private void configureListView() {
    listView.setCellFactory(
        lv ->
            new ListCell<>() {
              @Override
              protected void updateItem(TestScenario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText(null);
                  setStyle("");
                } else {
                  String displayText =
                      item.getScenarioName() + " - " + item.getScenarioDescription();
                  setText(
                      displayText.length() > 100
                          ? displayText.substring(0, 97) + "..."
                          : displayText);
                  setStyle(
                      "-fx-background-color: "
                          + (item.isCompleted() ? "lightgreen" : "salmon")
                          + ";");
                }
              }
            });

    listView.setOnMouseClicked(this::handleMouseClick);
  }

  private void handleMouseClick(MouseEvent event) {
    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
      TestScenario selectedScenario = listView.getSelectionModel().getSelectedItem();
      if (selectedScenario != null) {
        showDetailsDialog(selectedScenario);
      }
    }
  }

  private void showDetailsDialog(TestScenario scenario) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Test Scenario Details");
    alert.setHeaderText(scenario.getScenarioName());
    String content =
        "Description: "
            + scenario.getScenarioDescription()
            + "\nRequest: "
            + scenario.getRequest()
            + "\nExpected Response: "
            + scenario.getExpectedResponse()
            + "\nCompleted: "
            + (scenario.isCompleted() ? "Yes" : "No");
    alert.setContentText(content);
    alert.showAndWait();
  }

  public void addTestScenarios(List<TestScenario> scenarios) {
    listView.getItems().addAll(scenarios);
  }

  public void updateTestScenario(TestScenario updatedScenario) {
    Platform.runLater(
        () -> {
          for (int i = 0; i < listView.getItems().size(); i++) {
            TestScenario scenario = listView.getItems().get(i);
            if (scenario.getId() == updatedScenario.getId()) {
              listView.getItems().set(i, updatedScenario);
              break;
            }
          }
        });
  }
}
