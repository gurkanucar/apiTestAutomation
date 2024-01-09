package com.gucardev.apitestautomation;

import com.gucardev.apitestautomation.model.TestScenario;
import com.gucardev.apitestautomation.service.DragAndDropHandler;
import com.gucardev.apitestautomation.service.TestScenarioListViewManager;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
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
}
