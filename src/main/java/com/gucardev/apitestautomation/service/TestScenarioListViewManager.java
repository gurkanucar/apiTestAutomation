package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

                  // Style with margin and bottom border as separator
                  setStyle(
                      "-fx-font-size: 15px;" +
                              "-fx-background-color: "
                          + (item.isCompleted()
                              ? (item.isSuccess() ? "lightgreen" : "salmon")
                              : "#6fb7ff")
                          + "; -fx-text-fill: #3f3f3f;"
                          + "-fx-padding: 10;" // Margin around the text
                          + "-fx-border-color: transparent transparent grey transparent;" // Border
                                                                                          // color
                                                                                          // (separator)
                          + "-fx-border-width: 0 0 1 0;" // Border width (only bottom border)
                      );
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
    Alert alert =
        new Alert(scenario.isSuccess() ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
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

    // Status Description
    Label statusLabel = new Label("Status: ");
    statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    Label statusValue = new Label(scenario.getIncomingStatus());
    statusValue.setWrapText(true);

    // Method Description
    Label methodLabel = new Label("Method: ");
    methodLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    Label methodValue = new Label(scenario.getMethod().toUpperCase());
    methodValue.setWrapText(true);

    // Url Description
    Label urlLabel = new Label("Url: ");
    urlLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    Label urlValue = new Label(scenario.getUrl());
    urlValue.setWrapText(true);

    // Text Areas for Request, Response, and Incoming Response
    Node requestArea = createTextArea("Request: ", scenario.getRequest());
    Node responseArea = createTextArea("Expected Response: ", scenario.getExpectedResponse());

    Node incomingResponseArea =
        createTextArea("Incoming Response: ", scenario.getIncomingResponse());

    // Adding to grid
    grid.add(nameLabel, 0, 0);
    grid.add(nameValue, 1, 0);
    grid.add(descriptionLabel, 0, 1);
    grid.add(descriptionValue, 1, 1);
    grid.add(statusLabel, 0, 2);
    grid.add(statusValue, 1, 2);
    grid.add(methodLabel, 0, 3);
    grid.add(methodValue, 1, 3);
    grid.add(urlLabel, 0, 4);
    grid.add(urlValue, 1, 4);

    grid.add(new HBox(10, requestArea, responseArea), 0, 5, 2, 1);
    grid.add(incomingResponseArea, 0, 6, 2, 1);

    alert.getDialogPane().setContent(grid);
    alert.showAndWait();
  }

  private Node createTextArea(String label, String content) {
    Label textLabel = new Label(label);
    textLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    TextArea textArea = new TextArea(content);
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setPrefSize(500, 250);
    return new VBox(textLabel, textArea);
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
