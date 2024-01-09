package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.DialogUtil;
import com.gucardev.apitestautomation.HelloController;
import com.gucardev.apitestautomation.model.TestScenario;
import java.util.List;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class DragAndDropHandler {

  private final VBox dropZone;
  private final FileProcessor fileProcessor;
  private final HelloController controller;
  private final RequestSenderUtil requestSenderUtil;

  public DragAndDropHandler(VBox dropZone, HelloController controller) {
    this.dropZone = dropZone;
    this.requestSenderUtil = new RequestSenderUtil();
    this.fileProcessor = new FileProcessor(requestSenderUtil);
    this.controller = controller;
    setupDragAndDrop();
  }

  private void setupDragAndDrop() {
    dropZone.setOnDragOver(this::handleDragOver);
    dropZone.setOnDragDropped(this::handleDragDropped);
  }

  private void handleDragOver(DragEvent event) {
    if (event.getDragboard().hasFiles()) {
      event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
    }
    event.consume();
  }

  private void handleDragDropped(DragEvent event) {
    try {
      var db = event.getDragboard();
      boolean success = db.hasFiles();
      if (success) {
        db.getFiles()
            .forEach(
                file -> {
                  List<TestScenario> scenarios = fileProcessor.processFile(file.getAbsolutePath());
                  controller.addTestScenarios(scenarios);
                  fileProcessor.processScenariosIndependently(
                      scenarios, controller::updateTestScenario);
                });
      }
      event.setDropCompleted(success);
      event.consume();
    } catch (Exception e) {
      DialogUtil.showErrorDialogAsync("Error reading file: " + e.getMessage());
    }
  }
}
