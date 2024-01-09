package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {

  private static final String SCENARIO_NAME_COLUMN = "scenarioName";
  private static final String SCENARIO_DESCRIPTION_COLUMN = "scenarioDescription";
  private static final String EXPECTED_RESPONSE_COLUMN = "expectedResponse";
  private static final String REQUEST_COLUMN = "request";
  private static final String URL = "url";

  public List<TestScenario> readExcelFile(String filePath) {
    List<TestScenario> testScenarios = new ArrayList<>();

    try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
      Sheet sheet = workbook.getSheetAt(0);
      Row headerRow = sheet.getRow(0);

      int scenarioNameColIdx = findColumnIndex(headerRow, SCENARIO_NAME_COLUMN);
      int scenarioDescriptionColIdx = findColumnIndex(headerRow, SCENARIO_DESCRIPTION_COLUMN);
      int expectedResponseColIdx = findColumnIndex(headerRow, EXPECTED_RESPONSE_COLUMN);
      int requestColIdx = findColumnIndex(headerRow, REQUEST_COLUMN);

      if (scenarioNameColIdx == -1
          || scenarioDescriptionColIdx == -1
          || expectedResponseColIdx == -1
          || requestColIdx == -1) {
        throw new IllegalArgumentException("Required columns not found in the sheet");
      }

      for (Row row : sheet) {
        if (row.getRowNum() == 0) continue; // Skip header row

        String scenarioName = getCellValue(row, scenarioNameColIdx);
        String scenarioDescription = getCellValue(row, scenarioDescriptionColIdx);
        String expectedResponse = getCellValue(row, expectedResponseColIdx);
        String request = getCellValue(row, requestColIdx);

        TestScenario testScenario =
            new TestScenario(
                row.getRowNum(),
                scenarioName,
                scenarioDescription,
                request,
                expectedResponse,
                false);
        testScenarios.add(testScenario);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return testScenarios;
  }

  private int findColumnIndex(Row headerRow, String columnName) {
    for (Cell cell : headerRow) {
      if (columnName.equalsIgnoreCase(cell.getStringCellValue())) {
        return cell.getColumnIndex();
      }
    }
    return -1;
  }

  private String getCellValue(Row row, int columnIndex) {
    Cell cell = row.getCell(columnIndex);
    return cell != null ? cell.getStringCellValue() : "";
  }
}
