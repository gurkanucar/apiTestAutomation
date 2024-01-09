package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcessor {

  private final ExcelFileReader reader;
  private final ExecutorService executorService;

  public FileProcessor() {
    this.reader = new ExcelFileReader();
    this.executorService = Executors.newCachedThreadPool();
  }

  public List<TestScenario> processFile(String filePath) {
    log.info("Processing file: " + filePath);
    List<TestScenario> testScenarios = reader.readExcelFile(filePath);
    testScenarios.forEach(scenario -> log.info(scenario.toString()));
    return testScenarios;
  }

  public void processScenariosIndependently(
      List<TestScenario> scenarios, Consumer<TestScenario> updateUI) {
    for (TestScenario scenario : scenarios) {
      executorService.submit(
          () -> {
            try {
              // Process scenario
              boolean result = processScenario(scenario); // Implement this based on your logic
              scenario.setCompleted(result);

              // Update UI
              Platform.runLater(() -> updateUI.accept(scenario));
            } catch (Exception e) {
              e.printStackTrace(); // Handle exception as needed
            }
          });
    }
  }

  private boolean processScenario(TestScenario scenario) {
    // Add your scenario processing logic here
    // Return true or false based on the processing result
    return Math.random() > 0.5; // Placeholder return value
  }
}
