package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TestScenarioListViewManager {

  private final ListView<TestScenario> listView;

  public TestScenarioListViewManager(ListView<TestScenario> listView) {
    this.listView = listView;
    configureListView();
  }

  private void configureListView() {
    listView.setCellFactory(
        new Callback<>() {
          @Override
          public ListCell<TestScenario> call(ListView<TestScenario> lv) {
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
