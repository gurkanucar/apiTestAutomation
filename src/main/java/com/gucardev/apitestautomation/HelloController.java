package com.gucardev.apitestautomation;

import com.gucardev.apitestautomation.service.DragAndDropHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

@Slf4j
public class HelloController {

  @FXML private VBox dropZone;
  private DragAndDropHandler dragAndDropHandler;

  @FXML
  public void initialize() {
    dragAndDropHandler = new DragAndDropHandler(dropZone);
  }
}
