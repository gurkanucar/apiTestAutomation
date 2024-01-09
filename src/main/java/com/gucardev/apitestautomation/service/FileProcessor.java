package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
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
}
