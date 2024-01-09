package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.DialogUtil;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class DragAndDropHandler {

  private final VBox dropZone;
  private final FileProcessor fileProcessor;

  public DragAndDropHandler(VBox dropZone) {
    this.dropZone = dropZone;
    this.fileProcessor = new FileProcessor();
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
        db.getFiles().forEach(file -> fileProcessor.processFile(file.getAbsolutePath()));
      }
      event.setDropCompleted(success);
      event.consume();
    } catch (Exception e) {
      DialogUtil.showErrorDialogAsync("Error reading file: " + e.getMessage());
    }
  }
}
