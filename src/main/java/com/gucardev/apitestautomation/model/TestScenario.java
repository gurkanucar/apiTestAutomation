package com.gucardev.apitestautomation.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestScenario {
  private int id;
  private String scenarioName;
  private String scenarioDescription;
  private String request;
  private String expectedResponse;
  private String incomingResponse;
  private boolean completed;
}
