package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    alert.setHeaderText(null);

    GridPane grid = new GridPane();
    grid.setVgap(10);
    grid.setHgap(10);

    // Scenario Name
    Label nameLabel = new Label("Name: ");
    nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    Label nameValue = new Label(scenario.getScenarioName());
    nameValue.setWrapText(true);

    // Scenario Description
    Label descriptionLabel = new Label("Description: ");
    descriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    Label descriptionValue = new Label(scenario.getScenarioDescription());
    descriptionValue.setWrapText(true);

    // Text Areas for Request, Response, and Incoming Response
    TextArea requestArea = createTextArea("Request: ", scenario.getRequest());
    TextArea responseArea = createTextArea("Expected Response: ", scenario.getExpectedResponse());
    TextArea incomingResponseArea =
        createTextArea(
            "Incoming Response: ", scenario.getIncomingResponse()); // Assuming you have this field

    // Adding to grid
    grid.add(nameLabel, 0, 0);
    grid.add(nameValue, 1, 0);
    grid.add(descriptionLabel, 0, 1);
    grid.add(descriptionValue, 1, 1);
    grid.add(new HBox(10, requestArea, responseArea), 0, 2, 2, 1);
    grid.add(incomingResponseArea, 0, 3, 2, 1);

    alert.getDialogPane().setContent(grid);
    alert.showAndWait();
  }

  private TextArea createTextArea(String label, String content) {
    TextArea textArea = new TextArea(content);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setPrefSize(200, 100); // Adjust size as needed
    return textArea;
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
