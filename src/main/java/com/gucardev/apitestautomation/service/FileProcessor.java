package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javafx.application.Platform;

public class FileProcessor {

  private final ExcelFileReader reader;
  private final ExecutorService executorService;
  private final RequestSenderUtil requestSenderUtil;

  public FileProcessor(RequestSenderUtil requestSenderUtil) {
    this.requestSenderUtil = requestSenderUtil;
    this.reader = new ExcelFileReader();
    this.executorService = Executors.newCachedThreadPool();
  }

  public List<TestScenario> processFile(String filePath) {
    //    log.info("Processing file: " + filePath);
    List<TestScenario> testScenarios = reader.readExcelFile(filePath);
    //    testScenarios.forEach(scenario -> log.info(scenario.toString()));
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
    try {
      HttpResponse<String> response = requestSenderUtil.sendRequest(scenario);
      scenario.setIncomingResponse(response.body());
      scenario.setIncomingStatus(String.valueOf(response.statusCode()));
      return scenario.isCompleted();
    } catch (Exception e) {
      e.printStackTrace();
      scenario.setIncomingResponse(e.getMessage());
      return false;
    }
  }
}
