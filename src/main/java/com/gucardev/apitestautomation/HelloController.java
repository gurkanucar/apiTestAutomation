package com.gucardev.apitestautomation;

import com.gucardev.apitestautomation.model.TestScenario;
import com.gucardev.apitestautomation.service.DragAndDropHandler;
import com.gucardev.apitestautomation.service.TestScenarioListViewManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloController {

  @FXML private VBox mainVBox;
  @FXML private ListView<TestScenario> testScenarioListView;
  private DragAndDropHandler dragAndDropHandler;
  private TestScenarioListViewManager listViewManager;

  @FXML
  public void initialize() {
    listViewManager = new TestScenarioListViewManager(testScenarioListView);
    dragAndDropHandler = new DragAndDropHandler(mainVBox, this);
  }

  public void addTestScenarios(List<TestScenario> scenarios) {
    listViewManager.addTestScenarios(scenarios);
  }

  public void updateTestScenario(TestScenario updatedScenario) {
    listViewManager.updateTestScenario(updatedScenario);
  }

  @FXML
  public void clearAllProcessed() {
    testScenarioListView.getItems().clear();
  }

  @FXML
  public void exportToCSV() {
    List<TestScenario> scenarios = testScenarioListView.getItems();
    if (scenarios.stream().anyMatch(x -> !x.isCompleted())) {
      DialogUtil.showErrorDialog("Still under the testing!");
      return;
    }
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save CSV File");
    fileChooser.setInitialFileName(
        String.format("test_scenarios-%s.csv", LocalDateTime.now().toString().split("\\.")[0])
            .replace(":", "-"));
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File file = fileChooser.showSaveDialog(mainVBox.getScene().getWindow());
    if (file != null) {
      saveToFile(scenarios.stream().filter(x -> !x.isSuccess()).collect(Collectors.toList()), file);
    }
  }

  private void saveToFile(List<TestScenario> scenarios, File file) {
    try (PrintWriter writer = new PrintWriter(file)) {
      StringBuilder sb = new StringBuilder();
      sb.append("name,desc,method,url,request,expectedResp,incomingResp,incomingStatus");
      sb.append('\n');

      for (TestScenario scenario : scenarios) {
        sb.append(scenario.getScenarioName()).append(',');
        sb.append(scenario.getScenarioDescription()).append(',');
        sb.append(scenario.getMethod()).append(',');
        sb.append(scenario.getUrl()).append(',');
        sb.append(scenario.getRequest()).append(',');
        sb.append(scenario.getExpectedResponse()).append(',');
        sb.append(scenario.getIncomingResponse()).append(',');
        sb.append(scenario.getIncomingStatus()).append("\n\n\n\n");
      }

      writer.write(sb.toString());
    } catch (FileNotFoundException e) {
      DialogUtil.showErrorDialogAsync("Error saving file: " + e.getMessage());
    }
  }
}
