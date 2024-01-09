package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcessor {

  private final ExcelFileReader reader;

  public FileProcessor() {
    this.reader = new ExcelFileReader();
  }

  public List<TestScenario> processFile(String filePath) {
    log.info("Processing file: " + filePath);
    List<TestScenario> testScenarios = reader.readExcelFile(filePath);
    testScenarios.forEach(scenario -> log.info(scenario.toString()));
    return testScenarios;
  }

  public void processScenariosInBackground(
      List<TestScenario> scenarios, Consumer<TestScenario> updateUI) {
    Task<Void> task =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            for (TestScenario scenario : scenarios) {
              // Simulate a long-running task
              Thread.sleep(1000);

              // Process scenario and update its status
              boolean result =
                  processScenario(scenario); // Implement this method based on your logic
              scenario.setCompleted(result);

              // Update the UI
              Platform.runLater(() -> updateUI.accept(scenario));
            }
            return null;
          }
        };

    new Thread(task).start();
  }

  private boolean processScenario(TestScenario scenario) {
    // Add your scenario processing logic here
    // Return true or false based on the processing result
    return Math.random() > 0.5; // Placeholder return value
  }
}
